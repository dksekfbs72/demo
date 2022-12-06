package zerobase.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import zerobase.demo.type.UserStatus;

@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String userId;
	private String password;
	private String userName;
	private String phone;
	private String userAddr;
	private String emailAuthKey;
	private Boolean emailAuth;
	private UserStatus status;
	private LocalDateTime passwordChangeTime;

	public User() {
	}

	public void setStatus(String status) {
		if (status.equals("user")) this.status = UserStatus.user;
		if (status.equals("owner")) this.status = UserStatus.owner;
		if (status.equals("admin")) this.status = UserStatus.admin;
		if (status.equals("stop")) this.status = UserStatus.stop;
		if (status.equals("unregister")) this.status = UserStatus.unregister;
	}
}
