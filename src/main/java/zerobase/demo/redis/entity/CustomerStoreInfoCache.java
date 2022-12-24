package zerobase.demo.redis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.SelectStoreOpenType;
import zerobase.demo.common.type.SortType;
import zerobase.demo.common.type.StoreOpenCloseStatus;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.redis.customStructure.LocationJsonConverter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@RedisHash("StoreId")
public class CustomerStoreInfoCache {

	@Id
	private Integer id;
	private String name;
	private String storeAddr;
	private Integer orderCount;
	private String pictureUrl;
	private Double deliveryDistanceKm;
	private String summary;
	private StoreOpenCloseStatus openClose;
	private Integer deliveryTip;
	private Double commission;
	private LocalDateTime openCloseDt;
	private LocalDateTime regDt;

	public static CustomerStoreInfoCache fromEntity(Store store) {
		return CustomerStoreInfoCache.builder()
								.id(store.getId())
								.name(store.getName())
								.storeAddr(store.getStoreAddr())
								.orderCount(store.getOrderCount())
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

	public CustomerStoreInfo toDtoWithDistance(Double distanceKm) {
		return CustomerStoreInfo.builder()
							.id(id)
							.name(name)
							.storeAddr(storeAddr)
							.orderCount((orderCount))
							.pictureUrl((pictureUrl))
							.deliveryDistanceKm(deliveryDistanceKm)
							.summary(summary)
							.openClose(openClose)
							.deliveryTip(deliveryTip)
							.commission(commission)
							.openCloseDt(openCloseDt)
							.regDt(regDt)
							.distanceKm(distanceKm)
							.build();
	}

}
