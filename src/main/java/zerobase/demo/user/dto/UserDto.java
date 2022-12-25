package zerobase.demo.user.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.UserStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

	private String userId;
	private String password;
	private String userName;
	private String phone;
	private String userAddr;
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	private boolean emailAuth;

	public static UserDto fromRequest(UserDto.Request request) {
		return UserDto.builder()
			.userId(request.userId)
			.password(request.password)
			.userName(request.userName)
			.phone(request.phone)
			.userAddr(request.userAddr)
			.status(request.status)
			.emailAuth(true)
			.build();
	}

	public static UserDto fromEntity(User request) {
		return UserDto.builder()
			.userId(request.getUserId())
			.password("**************")
			.userName(request.getUserName())
			.phone(request.getPhone())
			.userAddr(request.getUserAddr())
			.status(request.getStatus())
			.emailAuth(true)
			.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {
		@NotNull
		private String userId;
		@NotNull
		private String password;
		@NotNull
		private String userName;
		@NotNull
		private String phone;
		@NotNull
		private String userAddr;
		@Enumerated(EnumType.STRING)
		private UserStatus status;
	}

	@NoArgsConstructor
	@Getter
	@Setter
	public static class Response<T> extends zerobase.demo.common.model.BaseResponse {
		private T resultList;
		public Response(T userDto, ResponseCode responseCode) {
			super(responseCode);
			this.resultList = userDto;
		}
		public Response(ResponseCode responseCode) {
			super(responseCode);
		}
	}
}
