package zerobase.demo.owner.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.entity.Store;

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
	private Boolean openClose;
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

		List<StoreInfo> list = new ArrayList<>();
		for(Store store : storeList) {
			list.add(fromEntity(store));
		}
		return list;
	}

}
