package zerobase.demo.common.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.catalina.Store;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import zerobase.demo.common.type.UserStatus;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
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

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private LocalDateTime passwordChangeDt;
	private LocalDateTime unregisterTime;

	//for owner
	@OneToMany
	private List<Store> storeList;
}
