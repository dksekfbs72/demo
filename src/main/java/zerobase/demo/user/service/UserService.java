package zerobase.demo.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserUpdateDto;

public interface UserService extends UserDetailsService {

	/**
	 * 신규 유저 생성
	 */
	boolean createUser(UserDto userDto);

	/**
	 * 관리자 유저 변경
	 */
	boolean adminUpdateUser(UserUpdateDto userDto, String myId);

	/**
	 * 접속중인 유저 정보 불러오기
	 */
	UserDto readMyInfo(String userId);

	/**
	 * 유저 패스워드 변경
	 */
	boolean changePassword(String myId, String password, String newPassword);

	/**
	 * 유저 탈퇴
	 */
	boolean userUnregister(String name, String password);

	/**
	 * 로그인 에러 코드 찾기
	 */
	ResponseCode getErrorCode(String errorCode);

	/**
	 * 이메일 인증
	 */
	boolean userEmailAuth(String emailAuthKey);

	/**
	 * 로그인
	 */
	UserDetails loadUserByUsername(String userId);

	/**
	 * 관리자 배달완료
	 */
	ResponseCode deliveryComplete(Integer orderId);

	/**
	 * 관리자 리뷰 내용 변경
	 */
	ReviewDto updateReview(ReviewDto fromRequest);

	/**
	 * 관리자 리뷰 삭제
	 */
	ResponseCode deleteReview(Integer reviewId);

	/**
	 * 관리자 회원 비밀번호 초기화
	 */
	ResponseCode adminResetPassword(Integer userId);

	/**
	 * 관리자 회원 상태 변경
	 */
	UserDto adminChangeUserStatus(Integer userId, String userStatus);
}
