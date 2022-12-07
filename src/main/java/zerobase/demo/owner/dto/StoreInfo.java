package zerobase.demo.owner.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.type.StoreOpenCloseStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInfo {

	private String name;
	private String storeAddr;
	private String pictureUrl;
	private Double deliveryDistanceKm;
	private String summary;
	private StoreOpenCloseStatus openClose;
	private Integer deliveryTip;
	private Double commission;
	private LocalDateTime openCloseDt;
	private LocalDateTime regDt;

	public static StoreInfo fromEntity(Store store) {

		return StoreInfo.builder()
			.name(store.getName())
			.storeAddr(store.getStoreAddr())
			.pictureUrl(store.getPictureUrl())
			.deliveryDistanceKm(store.getDeliveryDistanceKm())
			.summary(store.getSummary())
			.openClose(store.getOpenClose())
			.deliveryTip(store.getDeliveryTip())
			.commission(store.getCommission())
			.openCloseDt(store.getOpenCloseDt())
			.regDt(store.getRegDt())
			.build();
	}

	public static List<StoreInfo> fromEntity(List<Store> storeList) {

		return storeList.stream()
		.map(store -> fromEntity(store))
		.collect(Collectors.toList());
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class BaseResponse extends zerobase.demo.common.model.BaseResponse {
		private List<StoreInfo> storeInfoList;

		public static BaseResponse fromDtoList(List<StoreInfo> list){

			return BaseResponse.builder()
				.storeInfoList(list)
				.build();
		}
	}

}
