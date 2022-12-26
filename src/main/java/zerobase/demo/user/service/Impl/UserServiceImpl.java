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
import zerobase.demo.common.components.JobComponents;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.*;
import zerobase.demo.common.type.OrderStatus;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.common.components.MailComponents;
import zerobase.demo.order.repository.OrderRepository;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.repository.ReviewRepository;
import zerobase.demo.user.dto.NoticeDto;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserUpdateDto;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final MailComponents mailComponents;
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final ReviewRepository reviewRepository;
	private final JobComponents jobComponents;
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
				"<a href='http://zogiyoapp-env.eba-vh9egqq8.ap-northeast-2.elasticbeanstalk.com/user/emailAuth?emailAuthKey="+uuid+"'>회원가입 완료</a>");

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

		if (user.getStatus() == UserStatus.OWNER) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
		}

		if (user.getStatus()==UserStatus.ADMIN) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new org.springframework.security.core.userdetails.User(user.getUserId(),
			user.getPassword(), grantedAuthorities);
	}

	@Override
	public ResponseCode deliveryComplete(Integer orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new UserException(ResponseCode.ORDER_NOT_FOUND));
		order.setStatus(OrderStatus.DELIVERY_COMPLETE);
		Store store = storeRepository.findById(order.getRestaurantId()).get();
		store.setOrderCount(store.getOrderCount()+1);
		orderRepository.save(order);
		storeRepository.save(store);
		return ResponseCode.DELIVERY_SUCCESS;
	}

	@Override
	public ReviewDto updateReview(ReviewDto reviewDto) {
		Review review = reviewRepository.findByOrderId(reviewDto.getOrderId())
			.orElseThrow(() -> new UserException(ResponseCode.REVIEW_NOT_FOUND));

		review.setSummary(reviewDto.getSummary());
		review.setContent(reviewDto.getContent());

		reviewRepository.save(review);
		return ReviewDto.fromEntity(review);
	}

	@Override
	public ResponseCode deleteReview(Integer reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new UserException(ResponseCode.REVIEW_NOT_FOUND));

		reviewRepository.delete(review);
		return ResponseCode.DELETE_REVIEW_SUCCESS;
	}

	@Override
	public ResponseCode adminResetPassword(Integer userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

		String newPassword = randomWord();
		user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
		mailComponents.sendMail(user.getUserId(),
			"[조기요] 비밀번호 초기화",
			"<p>관리자에 의해 회원님의 비밀번호가 초기화되었습니다.</p>" +
				"<p>회원님의 임시 비밀번호는 "+ newPassword + " 입니다.</p>");
		userRepository.save(user);
		return ResponseCode.PASSWORD_RESET;
	}

	@Override
	public UserDto adminChangeUserStatus(Integer userId, String userStatus) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
		if (!userStatus.equals("USER") && !userStatus.equals("OWNER") && !userStatus.equals("STOP")) {
			throw new UserException(ResponseCode.USER_STATUS_NOT_FOUND);
		}
		user.setStatus(UserStatus.valueOf(userStatus));
		userRepository.save(user);
		return UserDto.fromEntity(user);
	}

	@Override
	public NoticeDto sendNotice(NoticeDto noticeDto) {
		jobComponents.noticeEmailSender(noticeDto.getUserStatus(), noticeDto.getNotice());
		return noticeDto;
	}

	static String randomWord() {
		StringBuilder Random= new StringBuilder();
		for (int i=0;; i++) {
			int x = (int) (Math.random()*75)+48;
			if (x >= 58 && x<=64) continue;
			if (x >= 91 && x<=96) continue;
			char ch = (char) x;
			Random.append(ch);
			if (Random.length()==8) break;
		}

		return Random.toString();
	}

}
