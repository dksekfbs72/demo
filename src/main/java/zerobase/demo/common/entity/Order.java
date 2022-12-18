package zerobase.demo.common.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerobase.demo.common.type.OrderStatus;

@Entity(name = "orderTable")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer price;

	@ElementCollection
	private List<Integer> menus = new ArrayList<>();

	private Integer userId;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	private Integer deliveryTime;
	private Integer restaurantId;
	private LocalDateTime orderTime;
	private Integer useCouponId;
}
