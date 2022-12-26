package zerobase.demo.customer.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.StoreOpenCloseStatus;
import zerobase.demo.owner.dto.MenuInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerStoreDetail {

	private Integer StoreId;
	private String name;
	private String storeAddr;
	private Integer orderCount;
	private String pictureUrl;
	private Double deliveryDistanceKm;
	private String summary;
	private StoreOpenCloseStatus openClose;
	private Integer deliveryTip;
	private LocalDateTime openCloseDt;
	private LocalDateTime regDt;
	private Double distanceKm;
	private List<MenuInfo> menuInfoList;

	public static CustomerStoreDetail from(CustomerStoreInfo customerStoreInfo, List<MenuInfo> menuInfoList) {
		return CustomerStoreDetail.builder()
			.StoreId(customerStoreInfo.getId())
			.name(customerStoreInfo.getName())
			.storeAddr(customerStoreInfo.getStoreAddr())
			.orderCount(customerStoreInfo.getOrderCount())
			.pictureUrl(customerStoreInfo.getPictureUrl())
			.deliveryDistanceKm(customerStoreInfo.getDeliveryDistanceKm())
			.summary(customerStoreInfo.getSummary())
			.openClose(customerStoreInfo.getOpenClose())
			.deliveryTip(customerStoreInfo.getDeliveryTip())
			.openCloseDt(customerStoreInfo.getOpenCloseDt())
			.regDt(customerStoreInfo.getRegDt())
			.distanceKm(customerStoreInfo.getDistanceKm())
			.menuInfoList(menuInfoList)
			.build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {
		Integer storeId;
		Double userLat;
		Double userLon;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Response extends BaseResponse {
		CustomerStoreDetail customerStoreDetail;

		public Response(ResponseCode code, CustomerStoreDetail customerStoreDetail) {
			super(code);
			this.customerStoreDetail = customerStoreDetail;
		}
	}
}
