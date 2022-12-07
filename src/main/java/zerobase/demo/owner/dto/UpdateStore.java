package zerobase.demo.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStore {

	private int id;
	private String name;
	private String storeAddr;
	private String pictureUrl;
	private Double deliveryDistanceKm;
	private String summary;
	private Integer deliveryTip;
	private Double commission;

	public static UpdateStore fromRequest(UpdateStore.Request request) {
		return UpdateStore.builder()
			.id(request.id)
			.name(request.name)
			.storeAddr(request.storeAddr)
			.pictureUrl(request.pictureUrl)
			.deliveryDistanceKm(request.deliveryDistanceKm)
			.summary(request.summary)
			.deliveryTip(request.deliveryTip)
			.commission(request.commission)
			.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		private int id;
		private String name;
		private String storeAddr;
		private String pictureUrl;
		private Double deliveryDistanceKm;
		private String summary;
		private Integer deliveryTip;
		private Double commission;
	}
}
