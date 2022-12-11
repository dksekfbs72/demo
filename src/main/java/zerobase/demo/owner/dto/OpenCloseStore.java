package zerobase.demo.owner.dto;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.StoreOpenCloseStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenCloseStore {

	private UserDetails loggedInUser;
	private Integer storeId;
	private StoreOpenCloseStatus openClose;

	public static OpenCloseStore fromRequest(OpenCloseStore.Request request) {
		return OpenCloseStore.builder()
			.storeId(request.storeId)
			.openClose(request.openClose)
			.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		private Integer storeId;
		private StoreOpenCloseStatus openClose;
	}

	public static class Response extends BaseResponse {
		public Response(ResponseCode code) {
			super(code);
		}
	}
}
