package zerobase.demo.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CreateStore {

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
