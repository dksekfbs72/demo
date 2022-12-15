package zerobase.demo.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zerobase.demo.common.config.AllExceptionHandler;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.customer.service.CustomerService;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.dto.ReviewRequest;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserDto.Response;
import zerobase.demo.user.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CustomerController extends AllExceptionHandler {

	private final CustomerService customerService;

	@PostMapping("/customer/addReview")
	Response userAddReview(@RequestBody ReviewRequest request, Principal principal) {
		boolean result = customerService.userAddReview(ReviewDto.fromRequest(request), principal.getName());

		return new Response(ResponseCode.ADD_REVIEW_SUCCESS);
	}

	@GetMapping("/customer/getMyOrder")
	public OrderDto.Response getMyOrderList(Principal principal) {

		return OrderDto.Response.fromDtoList(customerService.getMyOrderList(principal.getName()));
	}
}
