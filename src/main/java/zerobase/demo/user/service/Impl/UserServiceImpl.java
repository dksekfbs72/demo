package zerobase.demo.user.service.Impl;

import java.time.LocalDateTime;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.demo.common.entity.OrderTbl;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.StopUserException;
import zerobase.demo.common.exception.UnregisterUserException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.exception.UserNotEmailAuthException;
import zerobase.demo.common.exception.UserNotFindException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.common.components.MailComponents;
import zerobase.demo.order.repository.OrderRepository;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.repository.ReviewRepository;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserUpdateDto;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends UserException implements UserService,UserDetailsService {

	private final MailComponents mailComponents;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;

	@Override
	public boolean createUser(UserDto userDto) {

		Optional<User> optionalMember = userRepository.findByUserId(userDto.getUserId());
		if (optionalMember.isPresent()) {
			throw new UserException(ResponseCode.ALREADY_REGISTERED_ID);
		}

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
		Optional<User> optionalAdmin = userRepository.findByUserId(myId);
		if (!optionalAdmin.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}

//		if (!optionalAdmin.get().getStatus().name().equals("admin")) {
//			throw new UserException(ResponseCode.NOT_ADMIN_ROLL);
//		}

		Optional<User> optionalMember = userRepository.findByUserId(userDto.getUserId());
		if (!optionalMember.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}

		User user = optionalMember.get();
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
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (!optionalUser.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}
		User user = optionalUser.get();

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
		Optional<User> user = userRepository.findByUserId(myId);
		if (!user.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}
		User newUser = user.get();
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
		Optional<User> user = userRepository.findByUserId(myId);
		if (!user.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}
		User newUser = user.get();
		if (newUser.getPasswordChangeDt() != null && newUser.getPasswordChangeDt().plusMonths(1).isAfter(
			LocalDateTime.now())) {
			throw new UserException(ResponseCode.TOO_OFTEN_PASSWORD_CHANGE);
		}
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(password, newUser.getPassword())) {
			throw new UserException(ResponseCode.WRONG_PASSWORD);
		}
		newUser.setUnregisterTime(LocalDateTime.now());
		return true;
	}

	@Override
	public ResponseCode getErrorCode(String errorCode) {
		switch (errorCode) {
			case "USER_NOT_FIND": return ResponseCode.USER_NOT_FIND;
			case "USER_NOT_EMAIL_AUTH": return ResponseCode.USER_NOT_EMAIL_AUTH;
			case "USER_IS_STOP": return ResponseCode.USER_IS_STOP;
			case "UN_REGISTER_USER": return ResponseCode.UN_REGISTER_USER;
		}
		return ResponseCode.LOGIN_FAIL;
	}

	@Override
	public boolean userEmailAuth(String emailAuthKey) {
		Optional<User> optionalUser = userRepository.findByEmailAuthKey(emailAuthKey);
		if (!optionalUser.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}
		User user = optionalUser.get();

		user.setEmailAuth(true);
		userRepository.save(user);

		return true;
	}

	@Override
	public boolean userAddReview(ReviewDto fromRequest, String userId) {
		Optional<Review> optionalReview = reviewRepository.findByOrderId(fromRequest.getOrderId());
		if (optionalReview.isPresent()) {
			throw new UserException(ResponseCode.ALREADY_ADDED_REVIEW);
		}
		Optional<OrderTbl> optionalOrderTbl = orderRepository.findById(fromRequest.getOrderId());
		if (!optionalOrderTbl.isPresent()) {
			throw new UserException(ResponseCode.ORDER_NOT_FIND);
		}
		OrderTbl order = optionalOrderTbl.get();
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

		Optional<User> optionalMember = userRepository.findByUserId(userId);
		if (!optionalMember.isPresent()) {
			throw new UserNotFindException("USER_NOT_FIND");
		}

		User user = optionalMember.get();

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

		if (user.getStatus()==UserStatus.ADMIN) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new org.springframework.security.core.userdetails.User(user.getUserId(),
			user.getPassword(), grantedAuthorities);
	}


}
