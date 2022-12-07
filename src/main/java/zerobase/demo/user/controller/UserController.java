package zerobase.demo.user.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import zerobase.demo.common.config.AllExceptionHandler;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.model.UserInput;
import zerobase.demo.user.service.UserService;
import zerobase.demo.common.type.ResponseCode;

@Controller
@RequiredArgsConstructor
public class UserController extends AllExceptionHandler {

	private final UserService userService;

	@PostMapping("/user/create")
	public BaseResponse createUser(@ModelAttribute UserInput parameter) {

		boolean result = userService.createUser(
			parameter.getUserId(),
			parameter.getPassword(),
			parameter.getUserName(),
			parameter.getPhone(),
			parameter.getUserAddr(),
			parameter.getStatus());

		return new BaseResponse(ResponseCode.CREATE_USER_SUCCESS);
	}

	@PostMapping("/login/success")
	public BaseResponse login() {

		return new BaseResponse(ResponseCode.LOGIN_SUCCESS);
	}


	@GetMapping("/user/readMyInfo")
	UserDto readMyInfo(Principal principal) {

		return userService.readMyInfo(principal.getName());
	}

}
