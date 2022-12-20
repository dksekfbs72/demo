package zerobase.demo.user.controller;

import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.demo.common.config.AllExceptionHandler;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.dto.ReviewRequest;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserDto.Response;
import zerobase.demo.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController extends AllExceptionHandler {

	private final UserService userService;

	@PostMapping("/user/create")
	public UserDto.Response<ResponseCode> createUser(@RequestBody UserDto.Request parameter) {

		boolean result = userService.createUser(UserDto.fromRequest(parameter));

		return new UserDto.Response<>(ResponseCode.CREATE_USER_SUCCESS);
	}

	@PostMapping("/login/success")
	public UserDto.Response<ResponseCode> login() {

		return new UserDto.Response<>(ResponseCode.LOGIN_SUCCESS);
	}

	@GetMapping("/user/readMyInfo")
	UserDto readMyInfo(Principal principal) {
		if (principal==null) throw new UserException(ResponseCode.NOT_LOGGED);

		return userService.readMyInfo(principal.getName());
	}

	@PostMapping("/user/changePassword")
	UserDto.Response<ResponseCode> changePassword(Principal principal, @RequestParam String password, @RequestParam String newPassword) {
		boolean result = userService.changePassword(principal.getName(),password,newPassword);
		return new Response<>(ResponseCode.PASSWORD_CHANGE);
	}

	@DeleteMapping("/user/unregister")
	UserDto.Response<ResponseCode> userUnregister(Principal principal, @RequestParam String password) {
		boolean result = userService.userUnregister(principal.getName(), password);
		return new UserDto.Response<>(ResponseCode.USER_UNREGISTER);
	}

	@RequestMapping("/error/denied")
	UserDto.Response<ResponseCode> errorResponse(@RequestParam String errorCode) {

		ResponseCode responseCode = userService.getErrorCode(errorCode);

		return new Response<>(responseCode);
	}

	@PostMapping("/user/emailAuth")
	UserDto.Response<ResponseCode> userEmailAuth(@RequestParam String emailAuthKey) {
		boolean result = userService.userEmailAuth(emailAuthKey);
		return new Response<>(ResponseCode.EMAIL_AUTH_SUCCESS);
	}

}
