package zerobase.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import zerobase.demo.Input.UserInput;
import zerobase.demo.config.AllExceptionHandler;
import zerobase.demo.entity.User;
import zerobase.demo.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController extends AllExceptionHandler {

	private final UserService userService;

	@PostMapping("/user/create")
	User createUser(@ModelAttribute UserInput parameter) {

		return userService.createUser(parameter);

	}
}
