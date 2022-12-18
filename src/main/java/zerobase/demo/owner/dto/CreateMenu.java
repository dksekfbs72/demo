package zerobase.demo.owner.dto;

import java.time.LocalDateTime;

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
public class CreateMenu {

	private UserDetails loggedInUser; //로그인한 유저

	private Integer storeId; //연결된 store id
	
	private Integer price;
	private String name;
	private String pictureUrl;
	private String summary;

	public static CreateMenu fromRequest(Request request) {
			return CreateMenu.builder()
				.storeId(request.storeId)
				.price(request.price)
				.name(request.name)
				.pictureUrl(request.pictureUrl)
				.summary(request.summary)
				.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {
		private Integer storeId; //연결된 store id
		private Integer price;
		private String name;
		private String pictureUrl;
		private String summary;
	}

	public static class Response extends BaseResponse {
		public Response(ResponseCode code) {
			super(code);
		}
	}
}
