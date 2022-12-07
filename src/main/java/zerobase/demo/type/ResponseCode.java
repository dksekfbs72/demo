package zerobase.demo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
	ALREADY_REGISTERED_ID("이미 사용중인 아이디입니다."),
	STATUS_INPUT_ERROR("status는 user 혹은 owner만 가능합니다."),
	INTERNAL_SERVER_ERROR("서버 내부적 오류입니다."),
	USER_NOT_FIND("해당 유저가 업습니다."),
	USER_NOT_EMAIL_AUTH("이메일 인증 후 이용할 수 있습니다."),
	USER_IS_STOP("정지된 유저입니다."),
	NOT_ADMIN_ROLL("관리자 계정이 아닙니다."),



	CHANGE_USER_INFO_SUCCESS("유저 정보를 변경하였습니다."),
	CREATE_USER_SUCCESS("회원가입을 성공했습니다."),
	LOGIN_SUCCESS("로그인 성공"),
	LOGIN_FAIL("로그인 실패");

	private final String description;
}