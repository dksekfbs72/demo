package zerobase.demo.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.demo.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class AdminUserControllerTest {

	@InjectMocks
	private UserService userService;

}