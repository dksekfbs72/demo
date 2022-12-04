package zerobase.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.demo.model.UserDto;
import zerobase.demo.model.UserInput;
import zerobase.demo.config.AllExceptionHandler;
import zerobase.demo.model.ResponseResult;
import zerobase.demo.service.UserService;

@Controller
@RequiredArgsConstructor
public class AdminUserController extends AllExceptionHandler {

	private final UserService userService;

	@PutMapping("/admin/userUpdate")
	String createUser(@ModelAttribute UserDto parameter,
		@RequestParam String myId) {
		//유저의 아이디와 비밀번호는 변경할 수 없음
		System.out.println(myId);
		ResponseResult result = userService.adminUpdateUser(parameter, myId);

		return result.getMessage();
	}
}
