package zerobase.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Builder
@Data
public class UserInput {

	private String userId;
	private String password;
	private String userName;
	private String phone;
	private String userAddr;
	private String status;
}
