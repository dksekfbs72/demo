package zerobase.demo.customer.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.demo.common.components.MailComponents;
import zerobase.demo.common.entity.Menu;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.exception.CustomerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.customer.service.CustomerService;
import zerobase.demo.menu.dto.MenuDto;
import zerobase.demo.menu.repository.MenuRepository;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.order.repository.OrderRepository;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.repository.ReviewRepository;
import zerobase.demo.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final MenuRepository menuRepository;

	@Override
	public boolean userAddReview(ReviewDto fromRequest, String userId) {
		reviewRepository.findByOrderId(fromRequest.getOrderId())
				.ifPresent(t -> {throw new UserException(ResponseCode.ALREADY_ADDED_REVIEW);});

		Order order = orderRepository.findById(fromRequest.getOrderId())
				.orElseThrow(() -> new UserException(ResponseCode.ORDER_NOT_FOUND));

		if (!Objects.equals(order.getRestaurantId(), fromRequest.getRestaurantId())) {
			throw new UserException(ResponseCode.DIFF_ORDER_ID);
		}
		if (order.getOrderTime().plusDays(3).isBefore(LocalDateTime.now())) {
			throw new UserException(ResponseCode.TOO_OLD_AN_ORDER);
		}
		if (!order.getUserId().equals(userRepository.findByUserId(userId).get().getId())) {
			throw new UserException(ResponseCode.NOT_MY_ORDER);
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

		return false;
	}

	@Override
	public List<OrderDto> getMyOrderList(String userId) {

		List<Order> orderDtoList = orderRepository
				.findAllByUserId(userRepository.findByUserId(userId).get().getId());
		if (orderDtoList.isEmpty()) throw new CustomerException(ResponseCode.THERE_IS_NO_ORDER);

		return OrderDto.fromEntity(orderDtoList);
	}

	@Override
	public List<Review> getStoreReview(Integer storeId) {
		if (!storeRepository.findById(storeId).isPresent()) throw new CustomerException(ResponseCode.STORE_NOT_FOUND);
		List<Review> list = reviewRepository.findAllByRestaurantId(storeId);
		if (list.isEmpty()) throw new CustomerException(ResponseCode.THERE_IS_NO_REVIEW);
		return list;
	}

	@Override
	public List<MenuDto> getStoreMenu(Integer storeId) {
		if (!storeRepository.findById(storeId).isPresent()) throw new CustomerException(ResponseCode.STORE_NOT_FOUND);
		List<Menu> menuList = menuRepository.findAllByRestaurantId(storeId);
		if (menuList.isEmpty()) throw new CustomerException(ResponseCode.THERE_IS_NO_MENU);
		return MenuDto.fromEntity(menuList);
	}

	@Override
	public OrderDto putShoppingBasket(Integer storeId, String username, Integer menuId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(
			() -> new CustomerException(ResponseCode.MENU_NOT_FOUND));

		Integer userId = userRepository.findByUserId(username).get().getId();
		Optional<Order> order = orderRepository.findByUserIdAndStatus(userId, "SHOPPING");
		Order newOrder;
		if (!order.isPresent()) {
			newOrder = Order.builder()
				.price(menu.getPrice())
				.menus(Arrays.asList(menuId))
				.userId(userId)
				.status("SHOPPING")
				.restaurantId(storeId)
				.build();
		} else {
			newOrder = order.get();
			if (menu.getRestaurantId().equals(newOrder.getRestaurantId()))
				throw new CustomerException(ResponseCode.NOT_THIS_STORE_MENU);
			List<Integer> list = newOrder.getMenus();
			list.add(menuId);
			newOrder.setMenus(list);
			newOrder.setPrice(newOrder.getPrice()+menu.getPrice());

		}
		orderRepository.save(newOrder);
		return OrderDto.request(newOrder);
	}
}
