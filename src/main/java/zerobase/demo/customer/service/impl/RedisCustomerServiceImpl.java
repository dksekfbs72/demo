package zerobase.demo.customer.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.entity.Coupon;
import zerobase.demo.common.entity.Menu;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.entity.UserCouponTbl;
import zerobase.demo.common.exception.CustomerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.OrderStatus;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.SelectStoreOpenType;
import zerobase.demo.common.type.SoldOutStatus;
import zerobase.demo.common.type.SortType;
import zerobase.demo.common.type.StoreOpenCloseStatus;
import zerobase.demo.coupon.repository.CouponRepository;
import zerobase.demo.customer.dto.CustomerStoreDetail;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.customer.mapper.CustomerStoreMapper;
import zerobase.demo.customer.service.CustomerService;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.order.repository.OrderRepository;
import zerobase.demo.owner.dto.MenuInfo;
import zerobase.demo.owner.repository.MenuRepository;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.MenuService;
import zerobase.demo.redis.customStructure.Location;
import zerobase.demo.redis.customStructure.LocationJsonConverter;
import zerobase.demo.redis.entity.CustomerStoreInfoCache;
import zerobase.demo.redis.entity.CustomerStoreListCache;
import zerobase.demo.redis.repository.RedisSelectListRepository;
import zerobase.demo.redis.repository.RedisStoreInfoRepository;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.repository.ReviewRepository;
import zerobase.demo.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RedisCustomerServiceImpl implements CustomerService {

	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final MenuRepository menuRepository;
	private final CustomerStoreMapper customerStoreMapper;
	private final MenuService menuService;
	private final CouponRepository couponRepository;
	private final LocationJsonConverter locationJsonConverter;
	private final RedisSelectListRepository redisSelectListRepository;
	private final RedisStoreInfoRepository redisStoreInfoRepository;

	@Override
	public boolean userAddReview(ReviewDto fromRequest, String userId) {

		Order order = orderRepository.findById(fromRequest.getOrderId())
			.orElseThrow(() -> new UserException(ResponseCode.ORDER_NOT_FOUND));

		if (order.isReviewed()) {
			throw new CustomerException(ResponseCode.ALREADY_ADDED_REVIEW);
		}
		if (order.getStatus() != OrderStatus.DELIVERY_COMPLETE) {
			throw new CustomerException(ResponseCode.DID_NOT_DELIVERY_COMPLETE);
		}
		if (!Objects.equals(order.getRestaurantId(), fromRequest.getRestaurantId())) {
			throw new CustomerException(ResponseCode.DIFF_ORDER_ID);
		}
		if (order.getOrderTime().plusDays(3).isBefore(LocalDateTime.now())) {
			throw new CustomerException(ResponseCode.TOO_OLD_AN_ORDER);
		}
		if (!order.getUserId().equals(userRepository.findByUserId(userId).get().getId())) {
			throw new CustomerException(ResponseCode.NOT_MY_ORDER);
		}

		Review review = new Review().builder()
			.writerId(userRepository.findByUserId(userId).get().getId())
			.orderId(fromRequest.getOrderId())
			.restaurantId(fromRequest.getRestaurantId())
			.summary(fromRequest.getSummary())
			.content(fromRequest.getContent())
			.reviewAddTime(LocalDateTime.now())
			.build();

		reviewRepository.save(review);
		order.setReviewed(true);
		orderRepository.save(order);
		return false;
	}

	@Override
	public List<OrderDto> getMyOrderList(String userId) {

		List<Order> orderDtoList = orderRepository
			.findAllByUserId(userRepository.findByUserId(userId).get().getId());
		if (orderDtoList.isEmpty()) {
			throw new CustomerException(ResponseCode.THERE_IS_NO_ORDER);
		}

		return OrderDto.fromEntity(orderDtoList);
	}

	@Override
	public List<Review> getStoreReview(Integer storeId) {
		if (!storeRepository.findById(storeId).isPresent()) {
			throw new CustomerException(ResponseCode.STORE_NOT_FOUND);
		}
		List<Review> list = reviewRepository.findAllByRestaurantId(storeId);
		if (list.isEmpty()) {
			throw new CustomerException(ResponseCode.THERE_IS_NO_REVIEW);
		}
		return list;
	}

	@Override
	public OrderDto putShoppingBasket(Integer storeId, String username, Integer menuId,
		Integer count) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(
			() -> new CustomerException(ResponseCode.MENU_NOT_FOUND));
		if (menu.getSoldOutStatus() == SoldOutStatus.SOLD_OUT) {
			throw new CustomerException(ResponseCode.MENU_SOLD_OUT);
		}

		Integer userId = userRepository.findByUserId(username).get().getId();
		Optional<Order> order = orderRepository.findByUserIdAndStatus(userId, OrderStatus.SHOPPING);
		Order newOrder;
		if (!order.isPresent()) {
			List<Integer> newMenus = new ArrayList<>();
			for (int i = 0; i < count; i++) {
				newMenus.add(menuId);
			}
			newOrder = Order.builder()
				.price(menu.getPrice() * count)
				.menus(newMenus)
				.userId(userId)
				.status(OrderStatus.SHOPPING)
				.restaurantId(storeId)
				.reviewed(false)
				.build();
		} else {
			newOrder = order.get();
			// if (!menu.getRestaurantId().equals(newOrder.getRestaurantId())) {
			// 	throw new CustomerException(ResponseCode.NOT_THIS_STORE_MENU);
			// }
			List<Integer> newMenus = newOrder.getMenus();
			for (int i = 0; i < count; i++) {
				newMenus.add(menuId);
			}
			newOrder.setPrice(newOrder.getPrice() + (menu.getPrice() * count));
			newOrder.setMenus(newMenus);
		}
		orderRepository.save(newOrder);
		return OrderDto.request(newOrder);
	}

	@Override
	public OrderDto pullShoppingBasket(String username, Integer menuId) {
		Order order = orderRepository.findByUserIdAndStatus(
				userRepository.findByUserId(username).get().getId()
				, OrderStatus.SHOPPING)
			.orElseThrow(() -> new CustomerException(ResponseCode.ORDER_NOT_FOUND));

		Menu menu = menuRepository.findById(menuId).orElseThrow(
			() -> new CustomerException(ResponseCode.MENU_NOT_FOUND));

		List<Integer> newMenus = order.getMenus();
		if (!newMenus.contains(menuId)) {
			throw new CustomerException(ResponseCode.MENU_NOT_FOUND);
		}
		newMenus.remove(menuId);
		if (newMenus.isEmpty()) {
			orderRepository.delete(order);
			return null;
		}
		order.setMenus(newMenus);
		order.setPrice(order.getPrice() - menu.getPrice());
		orderRepository.save(order);

		return OrderDto.request(order);
	}

	@Override
	public OrderDto orderPayment(String username) {
		Order order = orderRepository.findByUserIdAndStatus(
				userRepository.findByUserId(username).get().getId()
				, OrderStatus.SHOPPING)
			.orElseThrow(() -> new CustomerException(ResponseCode.ORDER_NOT_FOUND));

		if (storeRepository.findById(order.getRestaurantId()).get().getOpenClose()
			== StoreOpenCloseStatus.CLOSE) {
			throw new CustomerException(ResponseCode.STORE_CLOSED);
		}

		order.setStatus(OrderStatus.PAYMENT);
		order.setDeliveryTime(60);
		order.setOrderTime(LocalDateTime.now());
		orderRepository.save(order);
		return OrderDto.request(order);
	}

	@Override
	public OrderDto cancelOrder(String username, Integer orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomerException(
			ResponseCode.ORDER_NOT_FOUND));

		if (order.getStatus() != OrderStatus.PAYMENT) {
			throw new CustomerException(ResponseCode.ORDER_NOT_PAYMENT);
		}
		if (order.getOrderTime().plusMinutes(20).isBefore(LocalDateTime.now())) {
			throw new CustomerException(ResponseCode.TOO_OLD_AN_ORDER);
		}

		order.setStatus(OrderStatus.CANCEL);
		order.setDeliveryTime(null);
		orderRepository.save(order);
		return OrderDto.request(order);
	}

	@Override
	public OrderDto useCoupon(String username, Integer couponId) {
		Integer userId = userRepository.findByUserId(username).get().getId();
		Order order = orderRepository.findByUserIdAndStatus(userId,
			OrderStatus.SHOPPING).orElseThrow(() -> new CustomerException(ResponseCode.ORDER_NOT_FOUND));
		UserCouponTbl userCoupon = couponRepository.findByUserIdAndAndCouponId(userId, couponId)
			.orElseThrow(() -> new CustomerException(ResponseCode.NOT_HAVE_COUPON));

		if (userCoupon.getUsedTime() != null) throw new CustomerException(ResponseCode.USED_COUPON);
		if (!userCoupon.getCoupon().getRestaurantId().equals(order.getRestaurantId()))
			throw new CustomerException(ResponseCode.NOT_THIS_STORE_COUPON);

		order.setPrice(order.getPrice()-userCoupon.getCoupon().getSalePrice());
		if (order.getPrice() < 0) throw new CustomerException(ResponseCode.THERE_IS_NO_DISCOUNT);

		List<Coupon> list = order.getUseCoupon();
		list.add(userCoupon.getCoupon());
		order.setUseCoupon(list);
		userCoupon.setUsedTime(LocalDateTime.now());

		orderRepository.save(order);
		couponRepository.save(userCoupon);
		return OrderDto.request(order);
	}

	@Override
	public CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.ListParam param) {

		//필수값 미입력
		if(param.getUserLat() == null || param.getUserLon() == null) throw new CustomerException(ResponseCode.BAD_REQUEST);
		//대한민국을 벗어날 경우
		if(isOutOfKorea(param.getUserLat(), param.getUserLon())) { throw new CustomerException(ResponseCode.BAD_REQUEST); }

		// 미입력 옵션 값을 기본 값 으로
		setNull2Default(param);

		//좌표 압축
		//좌표 소수점 3째자리 오차 : 약 110m
		//소수점 3째자리까지 남기고 반올림
		param.setUserLat(Math.round(param.getUserLat()*1000)/1000.0);
		param.setUserLon(Math.round(param.getUserLon()*1000)/1000.0);

		/**
		 * 레디스에서 값 가져와도 되는 경우
		 * 1. 키워드가 null 혹은 공백이고 (검색이 아니고)
		 * 2. 정렬 방식이 기본 정렬값인 거리순 정렬이며
		 * 3. 레디스에 키(위도, 경도)가 존재하며
		 * 4. 해당 키의 벨류값 중 maxCachedDistance(캐싱된 최대거리) 가 요청한 거리값 이상인 경우
		 */

		CustomerStoreListCache cacheValue = getValueIfCacheOk(param);

		//값 가져오기 가능한 경우
		if(cacheValue!=null) {

			//래디스에서는 불가능한 쿼리
			// List<CustomerStoreInfoCache> valueList
			// 	=redisStoreInfoRepository.findAllByIdIn(cacheValue.getStoreIdList());

			List<CustomerStoreInfo> responseData = new ArrayList<>();

			int size = cacheValue.getStoreIdList()==null?0:cacheValue.getStoreIdList().size();
			for(int i=0; i<size; i++){
				Optional<CustomerStoreInfoCache> optionalValue =
					redisStoreInfoRepository.findById(cacheValue.getStoreIdList().get(i));//오버헤드 가능성 있음
				CustomerStoreInfoCache storeInfoCache = optionalValue.get();

				//요청한 최대거리보다 먼 매장이 캐싱되어 있을 경우
				if(cacheValue.getMaxCachedDistance() > param.getMaxDistanceKm()) break;

				//OPEN만 검색했을 때는 OPEN만 담아준다.
				if(param.getOpenType() == SelectStoreOpenType.OPEN) {
					if(storeInfoCache.getOpenClose() == StoreOpenCloseStatus.OPEN) {
						responseData.add(
							storeInfoCache.toDtoWithDistance(cacheValue.getDistanceKmList().get(i)));
					}
				} else {
					responseData.add(
						storeInfoCache.toDtoWithDistance(cacheValue.getDistanceKmList().get(i)));
				}
			}

			return new CustomerStoreInfo.Response(ResponseCode.SELECT_STORE_SUCCESS, responseData);
		}

		//캐싱 대상이 아닌 경우 그냥 return
		if(!isNullOrEmpty(param.getKeyword()) || param.getSortType() != SortType.DISTANCE) {
			List<CustomerStoreInfo> customerStoreInfoList = customerStoreMapper.selectList(param);
			return new CustomerStoreInfo.Response(ResponseCode.SELECT_STORE_SUCCESS, customerStoreInfoList);
		}


		//기본적으로 open_close param이 뭐든간에 전부 조회해서 래디스에 저장한다.
		List<CustomerStoreInfo> customerStoreInfoList = customerStoreMapper.selectList(param);

		CustomerStoreListCache cache;
		try {
			cache = CustomerStoreListCache.builder()
									.id(locationJsonConverter
										.LocationToJson(new Location(param.getUserLat(), param.getUserLon())))
									.storeIdList(customerStoreInfoList.stream()
										.map(CustomerStoreInfo::getId).collect(Collectors.toList()))
									.distanceKmList(customerStoreInfoList.stream()
										.map(CustomerStoreInfo::getDistanceKm).collect(Collectors.toList()))
									.maxCachedDistance(param.getMaxDistanceKm())
									.build();
		} catch (JsonProcessingException e) {
			throw new CustomerException(ResponseCode.INTERNAL_SERVER_ERROR);
		}
		redisSelectListRepository.save(cache);

		//Open만 조회할 경우 걸러내기
		if(param.getOpenType() == SelectStoreOpenType.OPEN) {
			customerStoreInfoList = customerStoreInfoList.stream()
				.filter(x -> x.getOpenClose() == StoreOpenCloseStatus.OPEN)
				.collect(Collectors.toList());
		}

		return new CustomerStoreInfo.Response(ResponseCode.SELECT_STORE_SUCCESS, customerStoreInfoList);
	}

	private CustomerStoreListCache getValueIfCacheOk(CustomerStoreInfo.ListParam param) {

		if(!isNullOrEmpty(param.getKeyword()) || param.getSortType() != SortType.DISTANCE) return null;
		Optional<CustomerStoreListCache> optionalValue;
		try {
			optionalValue = redisSelectListRepository.findById(
				locationJsonConverter.LocationToJson(new Location(param.getUserLat(), param.getUserLon())));
		} catch (JsonProcessingException e) {
			throw new CustomerException(ResponseCode.INTERNAL_SERVER_ERROR);
		}

		if(!optionalValue.isPresent()) return null;

		CustomerStoreListCache value = optionalValue.get();

		if(value.getMaxCachedDistance() < param.getMaxDistanceKm()) return null;

		return value;
	}

	private boolean isNullOrEmpty(String s) {
		if(s == null || s.isEmpty()) return true;
		return false;
	}

	private void setNull2Default(CustomerStoreInfo.ListParam param)  {
		//기본값
		if(param.getMaxDistanceKm() == null) param.setMaxDistanceKm(3.0);
		if(param.getOpenType() ==null) param.setOpenType(SelectStoreOpenType.OPEN);
		if(param.getSortType() == null) param.setSortType(SortType.DISTANCE);
	}

	//검색이 아닌 경우에만 캐싱한다.
	private boolean isCacheTarget(CustomerStoreInfo.ListParam param) {
		if(param.getKeyword() != null) return false;
		return true;
	}

	@Override
	public CustomerStoreDetail.Response getStoreDetail(CustomerStoreDetail.Request request) {

		CustomerStoreInfo customerStoreInfo = getStoreInfo(
			CustomerStoreInfo.SelectParam
				.builder()
				.storeId(request.getStoreId())
				.userLat(request.getUserLat())
				.userLon(request.getUserLon())
				.build()
		);

		MenuInfo.Response response = menuService.getMenuInfoByStoreId(request.getStoreId());

		CustomerStoreDetail customerStoreDetail = CustomerStoreDetail.from(customerStoreInfo, response.getMenuInfoList());

		return new CustomerStoreDetail.Response(ResponseCode.SELECT_STORE_DETAIL_SUCCESS, customerStoreDetail);
	}

	private CustomerStoreInfo getStoreInfo(CustomerStoreInfo.SelectParam param) {

		Double userLat = param.getUserLat();
		Double userLon = param.getUserLon();

		//필수값 미입력
		if(userLat == null || userLon == null) throw new CustomerException(ResponseCode.BAD_REQUEST);
		if(isOutOfKorea(userLat, userLon)) {throw new CustomerException(ResponseCode.BAD_REQUEST);}

		CustomerStoreInfo customerStoreInfo =  customerStoreMapper.selectStoreById(param)
			.orElseThrow(() -> new CustomerException(ResponseCode.STORE_NOT_FOUND));

		return customerStoreInfo;
	}

	private boolean isOutOfKorea(Double userLat, Double userLon) {
		if(userLat < 33.12 || userLat > 38.58
			|| userLon < 125.11 || userLon > 131.86) {
			return true;
		}
		return false;
	}
}
