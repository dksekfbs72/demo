package zerobase.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
class AdminUserControllerTest {

	@InjectMocks
	private UserService userService;

}