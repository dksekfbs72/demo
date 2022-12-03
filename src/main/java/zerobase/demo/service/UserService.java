package zerobase.demo.service;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import zerobase.demo.Input.UserInput;
import zerobase.demo.entity.User;
import zerobase.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User createUser(UserInput parameter) {
		Optional<User> optionalMember = userRepository.findByUserId(parameter.getUserId());
		if (optionalMember.isPresent()) {
			return null;
		}

		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
		String uuid = UUID.randomUUID().toString();

		User user = User.builder()
			.userId(parameter.getUserId())
			.password(encPassword)
			.userName(parameter.getUserName())
			.phone(parameter.getPhone())
			.userAddr(parameter.getUserAddr())
			.emailAuthKey(uuid)
			.emailAuth(false)
			.status(parameter.getStatus())
			.passwordChangeTime(null)
			.build();

		userRepository.save(user);

		return user;
	}
}
