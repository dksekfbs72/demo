package zerobase.demo.owner.dto;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStore {

	UserDetails loggedInUser;

	private Integer storeId;
	private String name;
	private String storeAddr;
	private String pictureUrl;
	private Double deliveryDistanceKm;
	private String summary;
	private Integer deliveryTip;
	private Double commission;

	public static UpdateStore fromRequest(UpdateStore.Request request) {
		return UpdateStore.builder()
			.storeId(request.storeId)
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

		private Integer storeId;
		private String name;
		private String storeAddr;
		private String pictureUrl;
		private Double deliveryDistanceKm;
		private String summary;
		private Integer deliveryTip;
		private Double commission;
	}

	public static class Response extends BaseResponse {
		public Response(ResponseCode responseCode) {
			super(responseCode);
		}
	}
}
