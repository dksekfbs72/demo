package zerobase.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String userId;
	private String password;
	private String userName;
	private String phone;
	private String userAddr;
	private Boolean emailAuth;
	private String status;
	private LocalDateTime passwordChangeDt;

	//for owner
	@OneToMany
	private List<Store> storeList;
}
