package zerobase.demo.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.StopUserException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.exception.UserNotEmailAuthException;
import zerobase.demo.common.exception.UserNotFindException;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.UserStatus;

@Service
@RequiredArgsConstructor
public class UserService extends UserException implements UserDetailsService {

	private final UserRepository userRepository;

	public boolean createUser(String userId,
		String password,
		String userName,
		String phone,
		String userAddr,
		String status) {

		Optional<User> optionalMember = userRepository.findByUserId(userId);
		if (optionalMember.isPresent()) {
			throw new UserException(ResponseCode.ALREADY_REGISTERED_ID);
		}

		if (!status.equals("user") && !status.equals("owner")) {
			throw new UserException(ResponseCode.STATUS_INPUT_ERROR);
		}
		UserStatus userStatus;

		if (status.equals("user")) {
			userStatus = UserStatus.USER;
		} else {
			userStatus = UserStatus.OWNER;
		}

		String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		String uuid = UUID.randomUUID().toString();

		User user = User.builder()
			.userId(userId)
			.password(encPassword)
			.userName(userName)
			.phone(phone)
			.userAddr(userAddr)
			.emailAuthKey(uuid)
			.emailAuth(false)
			.status(userStatus)
			.passwordChangeTime(null)
			.build();

		userRepository.save(user);

		return true;
	}

	public boolean adminUpdateUser(String userId,
		String userName,
		String phone,
		String userAddr,
		String status,
		boolean emailAuth,
		String myId
	) {
		Optional<User> optionalAdmin = userRepository.findByUserId(myId);
		if (!optionalAdmin.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}

//		if (!optionalAdmin.get().getStatus().name().equals("admin")) {
//			throw new UserException(ResponseCode.NOT_ADMIN_ROLL);
//		}

		Optional<User> optionalMember = userRepository.findByUserId(userId);
		if (!optionalMember.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}

		User user = optionalMember.get();
		user.setUserAddr(userAddr);
		user.setUserName(userName);
		user.setPhone(phone);
		user.setStatus(status);
		user.setEmailAuth(emailAuth);

		userRepository.save(user);

		return true;
	}

//	public User readMyInfo(String myId) {
//
//	}

	@Override
	public UserDetails loadUserByUsername(String userId) {

		Optional<User> optionalMember = userRepository.findByUserId(userId);
		if (!optionalMember.isPresent()) {
			throw new UserNotFindException("아이디 정보가 존재하지 않습니다.");
		}

		User user = optionalMember.get();


		if (!user.getEmailAuth()) {
			throw new UserNotEmailAuthException("이메일 인증이 필요합니다.");
		}

		if (user.getStatus().name().equals("stop")) {
			throw new StopUserException("정지된 유저입니다.");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		if (user.getStatus().name().equals("admin")) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new org.springframework.security.core.userdetails.User(user.getUserId(),
			user.getPassword(), grantedAuthorities);
	}

	public UserDto readMyInfo(String userId) {
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if (!optionalUser.isPresent()) {
			throw new UserException(ResponseCode.USER_NOT_FIND);
		}
		User user = optionalUser.get();

		return UserDto.builder()
			.userId(user.getUserId())
			.userName(user.getUserName())
			.phone(user.getPhone())
			.userAddr(user.getUserAddr())
			.status(user.getStatus().name())
			.emailAuth(user.getEmailAuth())
			.build();
	}
}
