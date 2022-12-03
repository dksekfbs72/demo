package zerobase.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class OrderTbl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int price;

	//@Type(type = "json")
	//json 형태로 변경 예정
	private String menus;

	private int userId;
	private String status;
	private int deliveryTime;
	private int restaurantId;
	private LocalDateTime orderTime;
	private int useCouponId;

}
