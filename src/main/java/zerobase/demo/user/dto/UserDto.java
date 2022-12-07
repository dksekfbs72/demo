package zerobase.demo.user.dto;

import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class UserDto {

	private String userId;
	private String userName;
	private String phone;
	private String userAddr;
	private String status;
	private boolean emailAuth;

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
		@NotNull
		private String status;
	}
}
