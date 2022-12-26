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
public class SetCommission {
	private Integer storeId;
	private Double commission;


	@Getter
	@Setter
	public static class Request {
		private Integer storeId;
		private Double commission;
	}

	@Getter
	@Setter
	public static class Response extends BaseResponse {
		public Response(ResponseCode code) {
			super(code);
		}
	}

}
