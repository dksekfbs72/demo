package zerobase.demo.user.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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


	@ApiOperation("관리자 - 회원 정보 변경")
	@PutMapping("/userUpdate")
	UserUpdateDto.Response userUpdate(@RequestBody UserUpdateDto.Request parameter,
		Principal principal) {
		//유저의 아이디와 비밀번호는 변경할 수 없음
		boolean result = userService.adminUpdateUser(UserUpdateDto.fromRequest(parameter),
			principal.getName());
		logger.info("change user info -> " + parameter.getUserId());

		return new UserUpdateDto.Response(ResponseCode.CHANGE_USER_INFO_SUCCESS);
	}

	@ApiOperation("관리자 - 배달 완료 처리")
	@ApiImplicitParam(name = "orderId", value = "주문 번호")
	@PutMapping("/deliveryComplete")
	UserDto.Response<ResponseCode> deliveryComplete(@RequestParam Integer orderId) {
		return new UserDto.Response<>(userService.deliveryComplete(orderId));
	}

	@ApiOperation("관리자 - 리뷰 상세 보기")
	@GetMapping("/{storeId}/review")
	ReviewDto.Response<List<ReviewDto>> getStoreReview(@PathVariable Integer storeId) {

		return new ReviewDto.Response<>(ReviewDto.fromList(customerService.getStoreReview(storeId)),
			ResponseCode.GET_STORE_REVIEW_SUCCESS);
	}

	@ApiOperation("관리자 - 리뷰 내용 변경")
	@PutMapping("/updateReview")
	ReviewDto.Response<ReviewDto> updateReview(@RequestBody ReviewRequest reviewDto) {

		return new ReviewDto.Response<>(userService.updateReview(ReviewDto.fromRequest(reviewDto)),
			ResponseCode.REVIEW_UPDATE_SUCCESS);
	}

	@ApiOperation("관리자 - 리뷰 삭제")
	@ApiImplicitParam(name = "reviewId", value = "리뷰 번호")
	@DeleteMapping("/deleteReview")
	ReviewDto.Response<?> deleteReview(@RequestParam Integer reviewId) {

		return new Response<>(userService.deleteReview(reviewId));
	}

	@ApiOperation(value = "관리자 - 회원 비밀번호 초기화", notes = "비밀번호를 초기화 하면 해당 회원의 이메일로 임시 비밀번호가 전송됩니다.")
	@ApiImplicitParam(name = "userId", value = "회원 번호")
	@PutMapping("/resetPassword")
	UserDto.Response<?> adminResetPassword(@RequestParam Integer userId) {

		return new UserDto.Response<>(userService.adminResetPassword(userId));
	}

	@ApiOperation("관리자 - 유저 상태 변경")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "회원 번호")
		, @ApiImplicitParam(name = "userStatus", value = "회원 상태 (OWNER/STOP/USER)")
	})
	@PutMapping("/changeUserStatus")
	UserDto.Response<UserDto> adminChangeUserStatus(@RequestParam Integer userId
		, @RequestParam String userStatus) {

		return new UserDto.Response<>(userService.adminChangeUserStatus(userId, userStatus),
			ResponseCode.CHANGE_USER_INFO_SUCCESS);
	}

	@ApiOperation(value = "관리자 - 공지사항 전송", notes = "userStatus(USER/OWNER/STOP/ADMIN/UNREGISTER/ALL(전체))에 해당하는 회원에게만 전송됩니다.")
	@PostMapping("/sendNotice")
	UserDto.Response<NoticeDto> adminSendNotice(@RequestBody NoticeDto.request notice) {
		return new UserDto.Response<>(userService.sendNotice(NoticeDto.fromRequest(notice)),
			ResponseCode.SEND_NOTICE_SUCCESS);
	}
}
