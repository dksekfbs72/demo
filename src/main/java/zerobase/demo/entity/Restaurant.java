package zerobase.demo.entity;

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
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String restaurantAddr;
	private String pictureUrl;
	private int deliveryDistanceKm;
	private String summary;
	private boolean openClose;
	private int deliveryTip;
	private int commission;
	private int ownerId;

}
