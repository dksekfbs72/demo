package zerobase.demo.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
	ALREADY_REGISTERED_ID(Result.FAIL,"이미 사용중인 아이디입니다."),
	STATUS_INPUT_ERROR(Result.FAIL,"status는 user 혹은 owner만 가능합니다."),
	INTERNAL_SERVER_ERROR(Result.FAIL,"서버 내부적 오류입니다."),
	USER_NOT_FIND(Result.FAIL,"해당 유저가 없습니다."),
	USER_NOT_EMAIL_AUTH(Result.FAIL,"이메일 인증 후 이용할 수 있습니다."),
	USER_IS_STOP(Result.FAIL,"정지된 유저입니다."),
	NOT_ADMIN_ROLL(Result.FAIL,"관리자 계정이 아닙니다."),
	LOGIN_FAIL(Result.FAIL,"로그인 실패"),
	TOO_OFTEN_PASSWORD_CHANGE(Result.FAIL, "비밀번호를 변경한지 30일 경과 전입니다."),
	WRONG_PASSWORD(Result.FAIL, "비밀번호가 틀렸습니다."),
	UN_REGISTER_USER(Result.FAIL, "이미 탈퇴한 회원입니다."),
	NOT_LOGGED(Result.FAIL, "로그인 중이 아닙니다."),




	PASSWORD_CHANGE(Result.SUCCESS, "비밀번호를 성공적으로 변경했습니다."),
	LOGOUT_SUCCESS(Result.SUCCESS, "로그아웃에 성공했습니다."),
	CHANGE_USER_INFO_SUCCESS(Result.SUCCESS,"유저 정보를 변경하였습니다."),
	CREATE_USER_SUCCESS(Result.SUCCESS,"회원가입을 성공했습니다."),
	USER_UNREGISTER(Result.SUCCESS,"회원 탈퇴에 성공하였습니다."),
	LOGIN_SUCCESS(Result.SUCCESS,"로그인 성공");

	private final Result result;
	private final String description;
}