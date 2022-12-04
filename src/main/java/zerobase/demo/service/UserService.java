package zerobase.demo.service;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import zerobase.demo.model.UserDto;
import zerobase.demo.model.UserInput;
import zerobase.demo.entity.User;
import zerobase.demo.model.ResponseResult;
import zerobase.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public ResponseResult createUser(UserInput parameter) {
		Optional<User> optionalMember = userRepository.findByUserId(parameter.getUserId());
		if (optionalMember.isPresent()) {

			return new ResponseResult(false,"이미 존재하는 아이디입니다.");
		}

		if (!parameter.getStatus().equals("user") && !parameter.getStatus().equals("owner")) {
			return new ResponseResult(false, "status 는 user 혹은 owner 만 가능합니다.");
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

		return new ResponseResult(true,"회원가입에 성공하였습니다.");
	}

	public ResponseResult adminUpdateUser(UserDto parameter, String myId) {
		Optional<User> optionalAdmin = userRepository.findByUserId(myId);
		if (!optionalAdmin.isPresent()) {
			return new ResponseResult(false,"아이디가 존재하지 않습니다.");
		}

		System.out.println(optionalAdmin.get().getStatus());

		if (!optionalAdmin.get().getStatus().equals("admin")) {
			return new ResponseResult(false, "관리자 계정이 아닙니다.");
		}

		Optional<User> optionalMember = userRepository.findByUserId(parameter.getUserId());
		if (!optionalMember.isPresent()) {
			return new ResponseResult(false,"존재하지 않는 회원입니다.");
		}

		User user = optionalMember.get();
		user.setUserAddr(parameter.getUserAddr());
		user.setUserName(parameter.getUserName());
		user.setPhone(parameter.getPhone());
		user.setStatus(parameter.getStatus());
		user.setEmailAuth(parameter.isEmailAuth());

		userRepository.save(user);

		return new ResponseResult(true, "회원 정보를 수정하였습니다.");
	}
}
