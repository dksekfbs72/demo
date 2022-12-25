package zerobase.demo.user.controller;

import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.demo.DemoApplication;
import zerobase.demo.common.config.AllExceptionHandler;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.customer.service.CustomerService;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.dto.ReviewDto.Response;
import zerobase.demo.review.dto.ReviewRequest;
import zerobase.demo.user.dto.NoticeDto;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserUpdateDto;
import zerobase.demo.user.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController extends AllExceptionHandler {

	private final UserService userService;
	private final CustomerService customerService;
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);


	@PutMapping("/userUpdate")
	UserUpdateDto.Response userUpdate(@RequestBody UserUpdateDto.Request parameter,
		Principal principal) {
		//유저의 아이디와 비밀번호는 변경할 수 없음
		boolean result = userService.adminUpdateUser(UserUpdateDto.fromRequest(parameter),
			principal.getName());
		logger.info("change user info -> " + parameter.getUserId());

		return new UserUpdateDto.Response(ResponseCode.CHANGE_USER_INFO_SUCCESS);
	}

	@PutMapping("/deliveryComplete")
	UserDto.Response<ResponseCode> deliveryComplete(@RequestParam Integer orderId) {
		return new UserDto.Response<>(userService.deliveryComplete(orderId));
	}

	@GetMapping("/{storeId}/review")
	ReviewDto.Response<List<ReviewDto>> getStoreReview(@PathVariable Integer storeId) {

		return new ReviewDto.Response<>(ReviewDto.fromList(customerService.getStoreReview(storeId)), ResponseCode.GET_STORE_REVIEW_SUCCESS);
	}

	@PutMapping("/updateReview")
	ReviewDto.Response<ReviewDto> updateReview(@RequestBody ReviewRequest reviewDto) {

		return new ReviewDto.Response<>(userService.updateReview(ReviewDto.fromRequest(reviewDto)), ResponseCode.REVIEW_UPDATE_SUCCESS);
	}

	@DeleteMapping("/deleteReview")
	ReviewDto.Response<?> deleteReview(@RequestParam Integer reviewId) {

		return new Response<>(userService.deleteReview(reviewId));
	}

	@PutMapping("/resetPassword")
	UserDto.Response<?> adminResetPassword(@RequestParam Integer userId) {

		return new UserDto.Response<>(userService.adminResetPassword(userId));
	}

	@PutMapping("/changeUserStatus")
	UserDto.Response<UserDto> adminChangeUserStatus(@RequestParam Integer userId
		, @RequestParam String userStatus) {

		return new UserDto.Response<>(userService.adminChangeUserStatus(userId, userStatus),ResponseCode.CHANGE_USER_INFO_SUCCESS);
	}

	@PostMapping("/sendNotice")
	UserDto.Response<NoticeDto> adminSendNotice(@RequestBody NoticeDto.request notice) {
		return new UserDto.Response<>(userService.sendNotice(NoticeDto.fromRequest(notice)),
			ResponseCode.SEND_NOTICE_SUCCESS);
	}
}
