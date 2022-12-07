package zerobase.demo.dto;

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
}
