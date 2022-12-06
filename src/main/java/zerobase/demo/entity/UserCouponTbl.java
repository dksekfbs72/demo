package zerobase.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class UserCouponTbl {

	@Id
	private Integer userId;
	private Integer couponId;
	private LocalDateTime usedTime;
}
