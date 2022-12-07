package zerobase.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import zerobase.demo.config.AllExceptionHandler;
import zerobase.demo.model.ResponseResult;
import zerobase.demo.model.UserInput;
import zerobase.demo.service.UserService;
import zerobase.demo.type.ResponseCode;

@Controller
@RequiredArgsConstructor
public class UserController extends AllExceptionHandler {

	private final UserService userService;

	@PostMapping("/user/create")
	public ResponseResult createUser(@ModelAttribute UserInput parameter) {

		boolean result = userService.createUser(
			parameter.getUserId(),
			parameter.getPassword(),
			parameter.getUserName(),
			parameter.getPhone(),
			parameter.getUserAddr(),
			parameter.getStatus());

		return new ResponseResult(ResponseCode.CREATE_USER_SUCCESS);
	}

	@PostMapping("/login/success")
	public ResponseResult login() {

		return new ResponseResult(ResponseCode.LOGIN_SUCCESS);
	}

	@PostMapping("/login/fail")
	public ResponseResult loginFail(@ModelAttribute UserInput parameter) {

		return new ResponseResult(ResponseCode.LOGIN_FAIL);
	}

//	@GetMapping("/user/readMyInfo")
//	User readMyInfo(@RequestParam String myId) {
//
//		return userService.readMyInfo(myId);
//	}

}
