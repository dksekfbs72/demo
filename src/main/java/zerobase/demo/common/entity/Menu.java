package zerobase.demo.common.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import zerobase.demo.owner.dto.UpdateMenu;

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

	@Enumerated(EnumType.STRING)
	private SoldOutStatus soldOutStatus;

	@ManyToOne
	private Store store;

	public static Menu fromCreateMenu(CreateMenu dto) {
		return Menu.builder()
			.price(dto.getPrice())
			.name(dto.getName())
			.pictureUrl(dto.getPictureUrl())
			.summary(dto.getSummary())
			.build();
	}

	public void setFromUpdateMenu(UpdateMenu menu) {
		price = menu.getPrice();
		name = menu.getName();
		pictureUrl = menu.getPictureUrl();
		summary = menu.getSummary();
	}
}
