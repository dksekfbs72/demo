package zerobase.demo.redis.customStructure;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerobase.demo.customer.dto.CustomerStoreInfo;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Location implements Serializable {
	Double userLat; //위도
	Double userLon; //경도

	public static Location fromParam(CustomerStoreInfo.ListParam param) {
		return Location.builder()
				.userLat(param.getUserLat())
				.userLon(param.getUserLon())
				.build();
	}
}
