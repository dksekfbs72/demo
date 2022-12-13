package zerobase.demo.user.service.Impl;

import java.time.LocalDateTime;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.*;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.common.components.MailComponents;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.order.repository.OrderRepository;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.repository.ReviewRepository;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserUpdateDto;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final MailComponents mailComponents;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;

	@Override
	public boolean createUser(UserDto userDto) {

		userRepository.findByUserId(userDto.getUserId())
				.ifPresent(t -> {throw new UserException(ResponseCode.ALREADY_REGISTERED_ID);});

		if (userDto.getStatus()!=UserStatus.USER && userDto.getStatus()!=UserStatus.OWNER) {
			throw new UserException(ResponseCode.STATUS_INPUT_ERROR);
		}

		String encPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
		String uuid = UUID.randomUUID().toString();

		User user = User.builder()
			.userId(userDto.getUserId())
			.password(encPassword)
			.userName(userDto.getUserName())
			.phone(userDto.getPhone())
			.userAddr(userDto.getUserAddr())
			.emailAuthKey(uuid)
			.emailAuth(false)
			.status(userDto.getStatus())
			.passwordChangeDt(null)
			.build();

		userRepository.save(user);


		mailComponents.sendMail(user.getUserId(),
				"[조기요] 회원가입을 축하드립니다.",
				"<p>회원 가입을 축하드립니다.</p>" +
				"<p>아래 링크를 클릭하시면 회원 가입이 완료됩니다.</p>" +
				"<a href='http://localhost:8080/user/emailAuth?emailAuthKey="+uuid+"'>회원가입 완료</a>");

		return true;
	}

	@Override
	public boolean adminUpdateUser(UserUpdateDto userDto, String myId) {
		User user = userRepository.findByUserId(myId)
				.orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

		user.setUserAddr(userDto.getUserAddr());
		user.setUserName(userDto.getUserName());
		user.setPhone(userDto.getPhone());
		user.setStatus(userDto.getStatus());
		user.setEmailAuth(userDto.isEmailAuth());

		userRepository.save(user);

		return true;
	}

	@Override
	public UserDto readMyInfo(String userId) {
		User user = userRepository.findByUserId(userId)
				.orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

		return UserDto.builder()
			.userId(user.getUserId())
			.password("**********")
			.userName(user.getUserName())
			.phone(user.getPhone())
			.userAddr(user.getUserAddr())
			.status(user.getStatus())
			.emailAuth(user.getEmailAuth())
			.build();
	}

	@Override
	public boolean changePassword(String myId, String password, String newPassword) {
		User newUser = userRepository.findByUserId(myId)
				.orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

		if (newUser.getPasswordChangeDt() != null && newUser.getPasswordChangeDt().plusMonths(1).isAfter(
			LocalDateTime.now())) {
			throw new UserException(ResponseCode.TOO_OFTEN_PASSWORD_CHANGE);
		}
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(password, newUser.getPassword())) {
			throw new UserException(ResponseCode.WRONG_PASSWORD);
		}

		String encPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		newUser.setPassword(encPassword);
		newUser.setPasswordChangeDt(LocalDateTime.now());

		userRepository.save(newUser);
		return true;
	}

	@Override
	public boolean userUnregister(String myId, String password) {
		User newUser = userRepository.findByUserId(myId)
				.orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(password, newUser.getPassword())) {
			throw new UserException(ResponseCode.WRONG_PASSWORD);
		}
		newUser.setUnregisterTime(LocalDateTime.now());
		userRepository.save(newUser);
		return true;
	}

	@Override
	public ResponseCode getErrorCode(String errorCode) {
		switch (errorCode) {
			case "USER_NOT_FIND": return ResponseCode.USER_NOT_FOUND;
			case "USER_NOT_EMAIL_AUTH": return ResponseCode.USER_NOT_EMAIL_AUTH;
			case "USER_IS_STOP": return ResponseCode.USER_IS_STOP;
			case "UN_REGISTER_USER": return ResponseCode.UN_REGISTER_USER;
		}
		return ResponseCode.LOGIN_FAIL;
	}

	@Override
	public boolean userEmailAuth(String emailAuthKey) {
		User user = userRepository.findByEmailAuthKey(emailAuthKey)
				.orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

		user.setEmailAuth(true);
		userRepository.save(user);

		return true;
	}

	@Override
	public boolean userAddReview(ReviewDto fromRequest, String userId) {
		reviewRepository.findByOrderId(fromRequest.getOrderId())
				.ifPresent(t -> {throw new UserException(ResponseCode.ALREADY_ADDED_REVIEW);});

		Order order = orderRepository.findById(fromRequest.getOrderId())
				.orElseThrow(() -> new UserException(ResponseCode.ORDER_NOT_FOUND));

		if (!Objects.equals(order.getRestaurantId(), fromRequest.getRestaurantId())) {
			throw new UserException(ResponseCode.DIFF_ORDER_ID);
		}
		if (order.getOrderTime().plusDays(3).isBefore(LocalDateTime.now())) {
			throw new UserException(ResponseCode.TOO_OLD_AN_ORDER);
		}
		if (!order.getUserId().equals(userRepository.findByUserId(userId).get().getId())) {
			throw new UserException(ResponseCode.NOT_MY_ORDER);
		}

		Review review = new Review().builder()
				.writerId(userRepository.findByUserId(userId).get().getId())
				.orderId(fromRequest.getOrderId())
				.restaurantId(fromRequest.getRestaurantId())
				.summary(fromRequest.getSummary())
				.content(fromRequest.getContent())
				.reviewAddTime(LocalDateTime.now())
				.build();

		reviewRepository.save(review);

		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String userId) {

		User user = userRepository.findByUserId(userId)
				.orElseThrow(() -> new UserNotFindException("USER_NOT_FIND"));

		if (!user.getEmailAuth()) {
			throw new UserNotEmailAuthException("USER_NOT_EMAIL_AUTH");
		}

		if (user.getStatus()==UserStatus.STOP) {
			throw new StopUserException("USER_IS_STOP");
		}

		if (user.getUnregisterTime() != null) {
			throw new UnregisterUserException("UN_REGISTER_USER");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		if (user.getStatus().name().equals("OWNER")) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
		}

		if (user.getStatus()==UserStatus.ADMIN) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new org.springframework.security.core.userdetails.User(user.getUserId(),
			user.getPassword(), grantedAuthorities);
	}

	@Override
	public List<OrderDto> getMyOrderList(String userId) {

		List<Order> orderDtoList = orderRepository
				.findAllByUserId(userRepository.findByUserId(userId).get().getId());
		if (orderDtoList.isEmpty()) throw new UserException(ResponseCode.THERE_IS_NO_ORDER);


		return OrderDto.fromEntity(orderDtoList);
	}

}
