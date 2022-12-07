package zerobase.demo.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.type.StoreOpenCloseStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenCloseStore {

	private Integer id;
	private StoreOpenCloseStatus openClose;

	public static OpenCloseStore fromRequest(OpenCloseStore.Request request) {
		return OpenCloseStore.builder()
			.id(request.id)
			.openClose(request.openClose)
			.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		private Integer id;
		private StoreOpenCloseStatus openClose;
	}
}
