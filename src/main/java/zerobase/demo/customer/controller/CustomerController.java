package zerobase.demo.customer.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.customer.dto.CustomerStoreDetail;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.customer.service.CustomerService;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.dto.ReviewRequest;
import zerobase.demo.user.dto.UserDto.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer")
public class CustomerController {

	private final CustomerService customerService;

	//매장 리스트 조회
	@ApiOperation(value = "고객 - 매장 리스트 조회", notes = "request입니다.<br>"
		+ "{<br>"
		+ "//Required"
		+ "		\"userLat\" : \"Double\" ,<br>"
		+ "     \"userLon\" : \"Double\" ,<br>"
		+ "     //Optional<br>"
		+ "      \"maxDistanceKm\" : \"Double\" , //default 3km<br>"
		+ "      \"sortType\" : \"SortType\" , //default distance<br>"
		+ "      \"openType\" : \"SelectStoreOpenType\" , //default open<br>"
		+ "      \"keyword\" : \"String\")<br>"
		+ "}")
	@GetMapping("/store")
	public CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.ListParam listParam) throws
		JsonProcessingException {

		return customerService.getStoreList(listParam);
	}

	@ApiOperation(value = "고객 - 매장 상세 정보", notes = "request입니다.<br>"
		+ "{<br>"
		+ "		\"storeId\" : \"Integer\" ,<br>"
		+ "     \"userLat\" : \"Double\" ,<br>"
		+ "      \"userLon\" : \"Double\" "
		+ "}")
	@GetMapping("/store/detail")
	public CustomerStoreDetail.Response getStoreDetail(CustomerStoreDetail.Request request) {

		return customerService.getStoreDetail(request);
	}

	@ApiOperation(value = "고객 - 리뷰 작성", notes = "주문 후 3일이 지나면 리뷰를 남길 수 없습니다.")
	@PostMapping("/addReview")
	Response<ResponseCode> userAddReview(@RequestBody ReviewRequest request, Principal principal) {
		boolean result = customerService.userAddReview(ReviewDto.fromRequest(request), principal.getName());

		return new Response<>(ResponseCode.ADD_REVIEW_SUCCESS);
	}

	@ApiOperation("고객 - 내 주문 내역 확인")
	@GetMapping("/getMyOrder")
	public OrderDto.Response<List<OrderDto>> getMyOrderList(Principal principal) {

		return new OrderDto.Response<>(customerService.getMyOrderList(principal.getName()), ResponseCode.GET_MY_ORDER_SUCCESS);
	}

	@ApiOperation("고객 - 매장 리뷰 조회")
	@GetMapping("/{storeId}/review")
	public ReviewDto.Response<List<ReviewDto>> getStoreReview(@PathVariable Integer storeId) {

		return new ReviewDto.Response<>(ReviewDto.fromList(customerService.getStoreReview(storeId)), ResponseCode.GET_STORE_REVIEW_SUCCESS);
	}

	@ApiOperation(value = "고객 - 장바구니에 메뉴 담기", notes = "품절된 메뉴와 각기 다른 식당의 메뉴는 담을 수 없습니다.")
	@ApiImplicitParam(name = "menuId", value = "메뉴 번호")
	@PostMapping("/{storeId}")
	public OrderDto.Response<OrderDto> putShoppingBasket(@PathVariable Integer storeId, Principal principal, @RequestParam Integer menuId,
		@RequestParam Integer count) {

		return new OrderDto.Response<>(customerService.putShoppingBasket(storeId, principal.getName(), menuId, count)
			, ResponseCode.PUT_THIS_MENU);
	}

	@ApiOperation("고객 - 장바구니 메뉴 삭제")
	@ApiImplicitParam(name = "menuId", value = "메뉴 번호")
	@DeleteMapping("/pullShoppingBasket")
	public OrderDto.Response<OrderDto> pullShoppingBasket(Principal principal, @RequestParam Integer menuId) {

		return new OrderDto.Response<>(customerService.pullShoppingBasket(principal.getName(), menuId)
			, ResponseCode.PULL_THIS_MENU);
	}

	@ApiOperation("고객 - 주문 결제")
	@PutMapping("/payment")
	public OrderDto.Response<OrderDto> orderPayment(Principal principal) {

		return new OrderDto.Response<>(customerService.orderPayment(principal.getName()),
			ResponseCode.ORDER_SUCCESS);
	}

	@ApiOperation(value = "고객 - 주문 취소",notes = "결제 후 20분이 지나면 취소가 불가능합니다.")
	@ApiImplicitParam(name = "orderId", value = "주문 번호")
	@PutMapping("/cancelOrder")
	public OrderDto.Response<OrderDto> cancelOrder(Principal principal, @RequestParam Integer orderId) {
		return new OrderDto.Response<>(customerService.cancelOrder(principal.getName(), orderId),
			ResponseCode.ORDER_CANCEL_SUCCESS);
	}

	@ApiOperation(value = "고객 - 쿠폰 사용",notes = "사용 확정된 쿠폰은 환불이 불가능합니다.")
	@ApiImplicitParam(name = "couponId", value = "쿠폰 번호")
	@PutMapping("/useCoupon")
	public OrderDto.Response<OrderDto> useCoupon(Principal principal, @RequestParam Integer couponId) {
		return new OrderDto.Response<>(customerService.useCoupon(principal.getName(), couponId),
			ResponseCode.USE_COUPON_SUCCESS);
	}
}