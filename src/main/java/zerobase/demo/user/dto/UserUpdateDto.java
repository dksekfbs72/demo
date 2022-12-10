package zerobase.demo.user.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.UserStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {

	private String userId;
	private String userName;
	private String phone;
	private String userAddr;
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	private boolean emailAuth;

	public static UserUpdateDto fromRequest(UserUpdateDto.Request request) {
		return UserUpdateDto.builder()
			.userId(request.userId)
			.userName(request.userName)
			.phone(request.phone)
			.userAddr(request.userAddr)
			.status(request.status)
			.emailAuth(true)
			.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		private String userId;

		private String userName;

		private String phone;

		private String userAddr;
		@Enumerated(EnumType.STRING)
		private UserStatus status;
	}


	public static class Response extends zerobase.demo.common.model.BaseResponse {
		public Response(ResponseCode responseCode) {
			this.setCode(responseCode);
			this.setMessage(responseCode.getDescription());
			this.setResult(responseCode.getResult());
		}
	}

}
