package zerobase.demo.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
	ALREADY_REGISTERED_ID(Result.FAIL, "이미 사용중인 아이디입니다."),
	STATUS_INPUT_ERROR(Result.FAIL, "status는 user 혹은 owner만 가능합니다."),
	INTERNAL_SERVER_ERROR(Result.FAIL, "서버 내부적 오류입니다."),
	USED_COUPON(Result.FAIL, "이미 사용한 쿠폰입니다."),
	NOT_THIS_STORE_COUPON(Result.FAIL, "이 식당의 쿠폰이 아닙니다."),
	USER_NOT_FOUND(Result.FAIL, "해당 유저가 없습니다."),
	USER_NOT_EMAIL_AUTH(Result.FAIL, "이메일 인증 후 이용할 수 있습니다."),
	USER_STATUS_NOT_FOUND(Result.FAIL, "유저 상태 정보를 찾을 수 없습니다."),
	USER_IS_STOP(Result.FAIL, "정지된 유저입니다."),
	COUPON_NOT_FOUND(Result.FAIL, "쿠폰을 찾을 수 없습니다."),
	NOT_ADMIN_ROLL(Result.FAIL, "관리자 계정이 아닙니다."),
	NOT_THIS_STORE_MENU(Result.FAIL, "장바구니에는 한 식당의 메뉴만 담을 수 있습니다."),
	THERE_IS_NO_DISCOUNT(Result.FAIL, "할인 금액이 0보다 작습니다."),
	NOT_HAVE_COUPON(Result.FAIL, "존재하지 않는 쿠폰입니다."),
	MENU_SOLD_OUT(Result.FAIL, "품절된 메뉴입니다."),
	STORE_CLOSED(Result.FAIL, "해당 식당은 영업중이 아닙니다."),
	DID_NOT_DELIVERY_COMPLETE(Result.FAIL, "완료된 주문이 아닙니다."),
	ORDER_NOT_PAYMENT(Result.FAIL, "결제된 주문이 아닙니다."),
	REVIEW_NOT_FOUND(Result.FAIL, "리뷰를 찾을 수 없습니다."),

	CHANGE_USER_INFO_SUCCESS(Result.SUCCESS, "유저 정보를 변경하였습니다."),
	PASSWORD_RESET(Result.SUCCESS, "회원의 비밀번호를 초기화하였습니다."),
	SEND_NOTICE_SUCCESS(Result.SUCCESS, "공지 사항을 전송하였습니다."),
	DELETE_REVIEW_SUCCESS(Result.SUCCESS, "리뷰를 삭제하였습니다."),
	REVIEW_UPDATE_SUCCESS(Result.SUCCESS, "리뷰를 변경하였습니다."),
	USE_COUPON_SUCCESS(Result.SUCCESS, "쿠폰을 사용하셨습니다."),
	ORDER_CANCEL_SUCCESS(Result.SUCCESS, "주문을 취소하였습니다."),
	DELIVERY_SUCCESS(Result.SUCCESS, "주문 배달이 완료되었습니다."),
	ORDER_SUCCESS(Result.SUCCESS, "주문 결제가 완료되었습니다."),
	PULL_THIS_MENU(Result.SUCCESS, "장바구니에서 메뉴를 제거하였습니다."),
	PUT_THIS_MENU(Result.SUCCESS, "메뉴를 장바구니에 담았습니다."),
	GET_MY_ORDER_SUCCESS(Result.SUCCESS, "성공적으로 주문을 불러왔습니다."),
	CREATE_USER_SUCCESS(Result.SUCCESS, "회원가입을 성공했습니다."),
	GET_STORE_REVIEW_SUCCESS(Result.SUCCESS, "성공적으로 리뷰를 불러왔습니다."),
	GET_STORE_MENU_SUCCESS(Result.SUCCESS, "성공적으로 메뉴를 불러왔습니다."),
	LOGIN_SUCCESS(Result.SUCCESS, "로그인 성공"),
	THERE_IS_NO_REVIEW(Result.SUCCESS, "등록된 리뷰가 없습니다."),
	THERE_IS_NO_MENU(Result.SUCCESS, "등록된 메뉴가 없습니다."),
	LOGIN_FAIL(Result.FAIL, "로그인 실패"),
	ORDER_NOT_FOUND(Result.FAIL,"해당 주문이 없습니다."),
	TOO_OFTEN_PASSWORD_CHANGE(Result.FAIL, "비밀번호를 변경한지 30일 경과 전입니다."),
	WRONG_PASSWORD(Result.FAIL, "비밀번호가 틀렸습니다."),
	UN_REGISTER_USER(Result.FAIL, "이미 탈퇴한 회원입니다."),
	NOT_LOGGED(Result.FAIL, "로그인 중이 아닙니다."),
	NOT_ORDERED_THIS_STORE(Result.FAIL, "이 식당에서 주문한 적이 없습니다."),
	ALREADY_ADDED_REVIEW(Result.FAIL, "이미 리뷰를 남기셨습니다."),
	DIFF_ORDER_ID(Result.FAIL, "해당 가게의 주문이 아닙니다."),
	TOO_OLD_AN_ORDER(Result.FAIL, "너무 오래된 주문입니다."),
	NOT_MY_ORDER(Result.FAIL, "해당 유저의 주문이 아닙니다."),
	MENU_NOT_FOUND(Result.FAIL, "메뉴를 찾을 수 없습니다."),

	NOT_OWNER(Result.FAIL, "해당 유저는 owner가 아닙니다."),
	CREATE_STORE_SUCCESS(Result.SUCCESS, "가게 등록을 성공했습니다."),
	OPEN_STORE_SUCCESS(Result.SUCCESS, "가게를 열었습니다."),
	CLOSE_STORE_SUCCESS(Result.SUCCESS, "가게를 닫았습니다."),
	UPDATE_STORE_SUCCESS(Result.SUCCESS, "가게 정보를 변경하였습니다."),
	SELECT_STORE_SUCCESS(Result.SUCCESS, "가게 조회를 성공했습니다."),
	ALREADY_OPEN(Result.FAIL, "가게가 이미 열려 있습니다."),
	ALREADY_CLOSE(Result.FAIL, "가게가 이미 닫혀 있습니다."),
	STORE_NOT_FOUND(Result.FAIL, "존재하지 않는 가게 입니다."),
	NOT_AUTHORIZED(Result.FAIL, "권한이 없습니다."),

	PASSWORD_CHANGE(Result.SUCCESS, "비밀번호를 성공적으로 변경했습니다."),
	ADD_REVIEW_SUCCESS(Result.SUCCESS, "리뷰를 저장하였습니다."),
	EMAIL_AUTH_SUCCESS(Result.SUCCESS, "이메일 인증에 성공하셨습니다."),
	LOGOUT_SUCCESS(Result.SUCCESS, "로그아웃에 성공했습니다."),
	USER_UNREGISTER(Result.SUCCESS,"회원 탈퇴에 성공하였습니다."),
	THERE_IS_NO_ORDER(Result.SUCCESS,"주문내역이 없습니다."),

	CREATE_MENU_SUCCESS(Result.SUCCESS, "메뉴 등록을 성공했습니다."),
	ALREADY_SOLD_OUT(Result.FAIL, "이미 품절처리된 메뉴입니다."),
	ALREADY_FOR_SAIL(Result.FAIL, "이미 판매중인 메뉴입니다."),
	SET_SOLD_OUT_STATUS_SUCCESS(Result.SUCCESS, "메뉴 품절상태 변경 성공"),
	UPDATE_MENU_SUCCESS(Result.SUCCESS, "메뉴 수정을 성공했습니다."),
	SELECT_MENU_SUCCESS(Result.SUCCESS, "메뉴 조회를 성공했습니다."),

	BAD_REQUEST(Result.FAIL, "잘못된 요청입니다."),
	SELECT_STORE_DETAIL_SUCCESS(Result.SUCCESS, "가게 상세정보 조회 성공"),

	DELETE_MENU_SUCCESS(Result.SUCCESS, "메뉴 삭제 성공");



	private final Result result;
	private final String description;
}