package zerobase.demo.common.model;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Builder
@Data
public class UserInput {

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
