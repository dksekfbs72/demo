package zerobase.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.owner.dto.CreateStore;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String storeAddr;
	private String pictureUrl;
	private Double deliveryDistanceKm;
	private String summary;
	private Boolean openClose;
	private Integer deliveryTip;
	private Double commission;

	private LocalDateTime openCloseDt;

	@ManyToOne
	private User user;

	private LocalDateTime regDt;
	private LocalDateTime udtDt;


	public static Store from(CreateStore.Request request){

		return Store.builder()
			.name(request.getName())
			.commission(request.getCommission())
			.deliveryDistanceKm(request.getDeliveryDistanceKm())
			.deliveryTip(request.getDeliveryTip())
			.storeAddr(request.getStoreAddr())
			.summary(request.getSummary())
			.pictureUrl(request.getPictureUrl())
			.build();
	}

}
