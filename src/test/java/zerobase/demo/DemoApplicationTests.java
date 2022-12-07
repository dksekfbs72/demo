package zerobase.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import zerobase.demo.common.model.UserInput;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.user.service.UserService;
import zerobase.demo.common.type.UserStatus;


@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@DisplayName("회원 가입 성공")
	@Test
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	void createAccountSuccess() {
		//given
		UserInput user = UserInput.builder()
			.userId("Id")
			.userAddr("addr")
			.userName("name")
			.phone("phone")
			//.status(UserStatus.user)
			.password("password")
			.build();

		//when
		//boolean result = userService.createUser("Id","password",
		//	"name", "phone", "addr", "user");

		//then
		//assertTrue(result);
		/*
		 * 실제로 데이터가 수정되는지 확인하는 테스트 코드가 필요함
		 */
	}

}
