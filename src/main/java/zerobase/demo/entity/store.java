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
public class store extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String restaurantAddr;
	private String pictureUrl;
	private Integer deliveryDistanceKm;
	private String summary;
	private Boolean openClose;
	private Integer deliveryTip;
	private Integer commission;
	private Integer ownerId;

}
