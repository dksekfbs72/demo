package zerobase.demo.owner.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.entity.Menu;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.SoldOutStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuInfo {

	private Integer menuId;
	private Integer price;
	private String name;
	private String pictureUrl;
	private String summary;
	private SoldOutStatus soldOutStatus;

	public static MenuInfo fromEntity(Menu menu) {
		return MenuInfo.builder()
			.menuId(menu.getId())
			.price(menu.getPrice())
			.name(menu.getName())
			.pictureUrl(menu.getPictureUrl())
			.summary(menu.getSummary())
			.soldOutStatus(menu.getSoldOutStatus())
			.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Response extends BaseResponse {
		List<MenuInfo> menuInfoList;

		public Response(ResponseCode code, List<Menu> menuList) {
			super(code);

			menuInfoList = menuList.stream()
				.map(menu -> fromEntity(menu))
				.collect(Collectors.toList());
		}
	}
}
