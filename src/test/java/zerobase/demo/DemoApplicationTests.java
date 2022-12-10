package zerobase.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.user.service.Impl.UserServiceImpl;


@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@DisplayName("회원 가입 성공")
	@Test
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	void createAccountSuccess() {
		//given


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
