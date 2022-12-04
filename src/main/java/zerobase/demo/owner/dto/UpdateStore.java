package zerobase.demo.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateStore {

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
