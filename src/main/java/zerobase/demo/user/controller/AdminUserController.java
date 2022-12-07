package zerobase.demo.user.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerobase.demo.DemoApplication;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.common.config.AllExceptionHandler;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.user.service.UserService;
import zerobase.demo.common.type.ResponseCode;

@Controller
@RequiredArgsConstructor
public class AdminUserController extends AllExceptionHandler {

	private final UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);


	@PutMapping("/admin/userUpdate")
	BaseResponse userUpdate(@ModelAttribute UserDto parameter,
		@RequestParam String myId) {
		//유저의 아이디와 비밀번호는 변경할 수 없음
		boolean result = userService.adminUpdateUser(parameter.getUserId(),
			parameter.getUserName(),
			parameter.getPhone(),
			parameter.getUserAddr(),
			parameter.getStatus(),
			parameter.isEmailAuth(),
			myId);


		return new BaseResponse(ResponseCode.CHANGE_USER_INFO_SUCCESS);
	}
}
