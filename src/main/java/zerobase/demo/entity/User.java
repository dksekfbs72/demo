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

@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userId;
	private String password;
	private String userName;
	private String phone;
	private String userAddr;
	private String emailAuthKey;
	private boolean emailAuth;
	private String status;
	private LocalDateTime passwordChangeTime;

	public User() {

	}
}
