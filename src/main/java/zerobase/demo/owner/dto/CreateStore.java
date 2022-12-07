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
public class CreateStore {

	private Integer ownerId;
	private String name;
	private String storeAddr;
	private String summary;
	private String pictureUrl;
	private Double commission;
	private Double deliveryDistanceKm;
	private Integer deliveryTip;

	public static CreateStore fromRequest(CreateStore.Request request) {
		return CreateStore.builder()
			.ownerId(request.ownerId)
			.name(request.name)
			.storeAddr(request.storeAddr)
			.summary(request.summary)
			.pictureUrl(request.pictureUrl)
			.commission(request.commission)
			.deliveryDistanceKm(request.deliveryDistanceKm)
			.deliveryTip(request.deliveryTip)
			.build();
	}


	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		private Integer ownerId;
		private String name;
		private String storeAddr;
		private String summary;
		private String pictureUrl;
		private Double commission;
		private Double deliveryDistanceKm;
		private Integer deliveryTip;
	}
}
