package zerobase.demo.owner.dto;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.SoldOutStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetSoldOutStatus {

	private UserDetails loggedInUser; //로그인한 유저

	private Integer menuId;
	
	private SoldOutStatus soldOutStatus;

	public static SetSoldOutStatus fromRequest(Request request) {
			return SetSoldOutStatus.builder()
				.menuId(request.menuId)
				.soldOutStatus(request.soldOutStatus)
				.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {
		private Integer menuId;

		private SoldOutStatus soldOutStatus;
	}

	public static class Response extends BaseResponse {
		public Response(ResponseCode code) {
			super(code);
		}
	}
}
