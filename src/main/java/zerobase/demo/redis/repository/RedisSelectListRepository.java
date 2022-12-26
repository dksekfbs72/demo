package zerobase.demo.redis.repository;

import org.springframework.data.repository.CrudRepository;

import zerobase.demo.redis.customStructure.Location;
import zerobase.demo.redis.entity.CustomerStoreListCache;

//key : 위도,경도 Json / Value : 해당 키로 캐싱된 최대 거리, [결과의 StoreId, 키로부터의 거리]리스트
public interface RedisSelectListRepository
	extends CrudRepository<CustomerStoreListCache, String> {
}
