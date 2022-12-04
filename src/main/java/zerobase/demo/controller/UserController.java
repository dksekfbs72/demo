package zerobase.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import zerobase.demo.model.UserInput;
import zerobase.demo.config.AllExceptionHandler;
import zerobase.demo.model.ResponseResult;
import zerobase.demo.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController extends AllExceptionHandler {

	private final UserService userService;

	@PostMapping("/user/create")
	String createUser(@ModelAttribute UserInput parameter) {

		ResponseResult responseResult = userService.createUser(parameter);

		return responseResult.getMessage();

	}
}
