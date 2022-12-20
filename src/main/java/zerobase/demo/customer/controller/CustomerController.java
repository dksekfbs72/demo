package zerobase.demo.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.customer.dto.CustomerStoreDetail;
import zerobase.demo.customer.service.CustomerService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer")
public class CustomerController {

	private final CustomerService customerService;

	//매장 조회
	@GetMapping("/store")
	public CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.ListParam listParam) {

		return customerService.getStoreList(listParam);
	}

	@GetMapping("/store/detail")
	public CustomerStoreDetail.Response getStoreDetail(CustomerStoreDetail.Request request) {

		return customerService.getStoreDetail(request);
	}

import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.demo.common.config.AllExceptionHandler;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.customer.service.CustomerService;
import zerobase.demo.menu.dto.MenuDto;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.dto.ReviewRequest;
import zerobase.demo.user.dto.UserDto.Response;

@Controller
@RequiredArgsConstructor
public class CustomerController extends AllExceptionHandler {

	private final CustomerService customerService;

	@PostMapping("/customer/addReview")
	Response<ResponseCode> userAddReview(@RequestBody ReviewRequest request, Principal principal) {
		boolean result = customerService.userAddReview(ReviewDto.fromRequest(request), principal.getName());

		return new Response<>(ResponseCode.ADD_REVIEW_SUCCESS);
	}

	@GetMapping("/customer/getMyOrder")
	public OrderDto.Response<List<OrderDto>> getMyOrderList(Principal principal) {

		return new OrderDto.Response<>(customerService.getMyOrderList(principal.getName()), ResponseCode.GET_MY_ORDER_SUCCESS);
	}

	@GetMapping("/customer/{storeId}/review")
	public ReviewDto.Response getStoreReview(@PathVariable Integer storeId) {

		return new ReviewDto.Response(customerService.getStoreReview(storeId), ResponseCode.GET_STORE_REVIEW_SUCCESS);
	}

	@GetMapping("/customer/{storeId}/menu")
	public MenuDto.Response<List<MenuDto>> getStoreMenu(@PathVariable Integer storeId) {

		return new MenuDto.Response<>(customerService.getStoreMenu(storeId), ResponseCode.GET_STORE_MENU_SUCCESS);
	}

	@PostMapping("/customer/{storeId}")
	public OrderDto.Response<OrderDto> putShoppingBasket(@PathVariable Integer storeId, Principal principal, @RequestParam Integer menuId,
		@RequestParam Integer count) {

		return new OrderDto.Response<>(customerService.putShoppingBasket(storeId, principal.getName(), menuId, count)
										, ResponseCode.PUT_THIS_MENU);
	}

	@DeleteMapping("/customer/pullShoppingBasket")
	public OrderDto.Response<OrderDto> pullShoppingBasket(Principal principal, @RequestParam Integer menuId) {

		return new OrderDto.Response<>(customerService.pullShoppingBasket(principal.getName(), menuId)
			, ResponseCode.PULL_THIS_MENU);
	}

	@PutMapping("/customer/payment")
	public OrderDto.Response<OrderDto> orderPayment(Principal principal) {

		return new OrderDto.Response<>(customerService.orderPayment(principal.getName()),
			ResponseCode.ORDER_SUCCESS);
	}

	@PutMapping("/customer/cancelOrder")
	public OrderDto.Response<OrderDto> cancelOrder(Principal principal, @RequestParam Integer orderId) {
		return new OrderDto.Response<>(customerService.cancelOrder(principal.getName(), orderId),
			ResponseCode.ORDER_CANCEL_SUCCESS);
	}
}
