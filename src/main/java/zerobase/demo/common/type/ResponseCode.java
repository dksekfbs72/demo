package zerobase.demo.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
	ALREADY_REGISTERED_ID(Result.FAIL, "이미 사용중인 아이디입니다."),
	STATUS_INPUT_ERROR(Result.FAIL, "status는 user 혹은 owner만 가능합니다."),
	INTERNAL_SERVER_ERROR(Result.FAIL, "서버 내부적 오류입니다."),
	USER_NOT_FIND(Result.FAIL, "해당 유저가 없습니다."),
	USER_NOT_EMAIL_AUTH(Result.FAIL, "이메일 인증 후 이용할 수 있습니다."),
	USER_IS_STOP(Result.FAIL, "정지된 유저입니다."),
	NOT_ADMIN_ROLL(Result.FAIL, "관리자 계정이 아닙니다."),

	CHANGE_USER_INFO_SUCCESS(Result.SUCCESS, "유저 정보를 변경하였습니다."),
	CREATE_USER_SUCCESS(Result.SUCCESS, "회원가입을 성공했습니다."),
	LOGIN_SUCCESS(Result.SUCCESS, "로그인 성공"),
	LOGIN_FAIL(Result.FAIL, "로그인 실패"),

	NOT_LOGGED_IN(Result.FAIL, "로그인 정보가 없습니다."),

	CREATE_STORE_SUCCESS(Result.SUCCESS, "가게 등록을 성공했습니다."),
	UPDATE_STORE_SUCCESS(Result.SUCCESS, "가게 정보를 변경하였습니다."),
	SELECT_STORE_SUCCESS(Result.SUCCESS, "가게 조회를 성공했습니다."),
	ALREADY_OPEN(Result.FAIL, "가게가 이미 열려 있습니다."),
	ALREADY_CLOSE(Result.FAIL, "가게가 이미 닫혀 있습니다."),
	STORE_NOT_FOUND(Result.FAIL, "존재하지 않는 가게 입니다."),
	NOT_AUTHORIZED(Result.FAIL, "권한이 없습니다.");





	private final Result result;
	private final String description;
}