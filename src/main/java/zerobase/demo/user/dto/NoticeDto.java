package zerobase.demo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import zerobase.demo.common.type.UserStatus;

@Getter
@Builder
@AllArgsConstructor
public class NoticeDto {
	private String userStatus;
	private String notice;

	@Getter
	public static class request {
		private String userStatus;
		private String notice;
	}

	public static NoticeDto fromRequest(NoticeDto.request request) {
		return NoticeDto.builder()
			.userStatus(request.userStatus)
			.notice(request.notice)
			.build();
	}
}
