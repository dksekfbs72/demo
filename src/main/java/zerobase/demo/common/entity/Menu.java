package zerobase.demo.common.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerobase.demo.common.type.SoldOutStatus;
import zerobase.demo.owner.dto.CreateMenu;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer price;
	private String name;
	private String pictureUrl;
	private String summary;
	private SoldOutStatus soldOutStatus;

	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	public static Menu fromCreateMenu(CreateMenu dto) {
		return Menu.builder()
			.price(dto.getPrice())
			.name(dto.getName())
			.pictureUrl(dto.getPictureUrl())
			.summary(dto.getSummary())
			.build();
	}
}
