package zerobase.demo.redis.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerobase.demo.redis.customStructure.Location;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@IdClass(Location.class)
@RedisHash(value = "Location", timeToLive = 300)
public class CustomerStoreListCache {

	@Id
	String id;

	Double maxCachedDistance; //캐싱된 데이터가 최대 몇키로 까지인지

	private List<Integer> storeIdList;
	private List<Double> distanceKmList;

}
