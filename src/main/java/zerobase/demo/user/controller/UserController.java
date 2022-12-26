package zerobase.demo.user.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
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
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserDto.Response;
import zerobase.demo.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController extends AllExceptionHandler {

	private final UserService userService;

	@ApiOperation(value = "비회원 - 회원 가입", notes = "<p>status는 OWNER 혹은 USER만 가능합니다.<p>"
		+ "<p>아이디 = 이메일<p>")
	@PostMapping("/user/create")
	public UserDto.Response<ResponseCode> createUser(@RequestBody UserDto.Request parameter) {

		boolean result = userService.createUser(UserDto.fromRequest(parameter));

		return new UserDto.Response<>(ResponseCode.CREATE_USER_SUCCESS);
	}

	@PostMapping("/login/success")
	public UserDto.Response<ResponseCode> login() {

		return new UserDto.Response<>(ResponseCode.LOGIN_SUCCESS);
	}

	@ApiOperation("회원 - 내 정보 보기")
	@GetMapping("/user/readMyInfo")
	UserDto readMyInfo(Principal principal) {
		if (principal == null) {
			throw new UserException(ResponseCode.NOT_LOGGED);
		}

		return userService.readMyInfo(principal.getName());
	}

	@ApiOperation("회원 - 비밀번호 변경")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "password", value = "기존 비밀번호", required = true)
		, @ApiImplicitParam(name = "newPassword", value = "새로운 비밀번호", required = true)
	})
	@PostMapping("/user/changePassword")
	UserDto.Response<ResponseCode> changePassword(Principal principal,
		@RequestParam String password, @RequestParam String newPassword) {
		boolean result = userService.changePassword(principal.getName(), password, newPassword);
		return new Response<>(ResponseCode.PASSWORD_CHANGE);
	}

	@ApiOperation("회원 - 회원 탈퇴")
	@ApiImplicitParam(name = "password", value = "비밀번호", required = true)
	@DeleteMapping("/user/unregister")
	UserDto.Response<ResponseCode> userUnregister(Principal principal,
		@RequestParam String password) {
		boolean result = userService.userUnregister(principal.getName(), password);
		return new UserDto.Response<>(ResponseCode.USER_UNREGISTER);
	}

	@RequestMapping("/error/denied")
	UserDto.Response<ResponseCode> errorResponse(@RequestParam String errorCode) {

		ResponseCode responseCode = userService.getErrorCode(errorCode);

		return new Response<>(responseCode);
	}

	@ApiOperation(value = "이메일 인증", notes = "회원가입시 인증키를 포함한 링크를 메일로 전송합니다")
	@ApiImplicitParam(name = "emailAuthKey", value = "인증키", required = true)
	@PostMapping("/user/emailAuth")
	UserDto.Response<ResponseCode> userEmailAuth(@RequestParam String emailAuthKey) {
		boolean result = userService.userEmailAuth(emailAuthKey);
		return new Response<>(ResponseCode.EMAIL_AUTH_SUCCESS);
	}

}
