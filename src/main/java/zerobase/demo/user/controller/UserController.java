package zerobase.demo.user.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zerobase.demo.common.config.AllExceptionHandler;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController extends AllExceptionHandler {

	private final UserService userService;

	@PostMapping("/user/create")
	public UserDto.BaseResponse createUser(@RequestBody UserDto.Request parameter) {

		System.out.println();

		boolean result = userService.createUser(UserDto.fromRequest(parameter));

		return new UserDto.BaseResponse(ResponseCode.CREATE_USER_SUCCESS);
	}

	@PostMapping("/login/success")
	public UserDto.BaseResponse login() {

		return new UserDto.BaseResponse(ResponseCode.LOGIN_SUCCESS);
	}


	@GetMapping("/user/readMyInfo")
	UserDto readMyInfo(Principal principal) {

		return userService.readMyInfo(principal.getName());
	}

}
