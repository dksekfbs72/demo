package zerobase.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import zerobase.demo.Input.UserInput;
import zerobase.demo.entity.User;
import zerobase.demo.service.UserService;


@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

	@InjectMocks
	private UserService userService;

	@DisplayName("회원 가입 성공")
	@Test
	@Transactional
	void createAccountSuccess() {
		//given
		UserInput user = UserInput.builder()
			.userId("Id")
			.userAddr("addr")
			.userName("name")
			.phone("phone")
			.status("status")
			.password("password")
			.build();

		//when
		User result = userService.createUser(user);

		//then
		assertEquals(result.getUserId(), "Id");
		assertEquals(result.getUserAddr(), "addr");
		assertEquals(result.getUserName(), "name");
		assertEquals(result.getPhone(), "phone");
		assertEquals(result.getStatus(), "status");
		//비밀번호는 암호화되어 같으면 안됨
		assertNotEquals(result.getPassword(), "password");
		assertNull(result.getPasswordChangeTime());
		assertFalse(result.isEmailAuth());

	}

}
