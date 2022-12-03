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
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int price;
	private String name;
	private String pictureUrl;
	private String summary;
	private boolean soldOut;
	private int restaurantId;
	private LocalDateTime dropMenuTime;
}
