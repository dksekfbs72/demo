package zerobase.demo.redis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import zerobase.demo.redis.entity.CustomerStoreInfoCache;

//Key : storeId
public interface RedisStoreInfoRepository
	extends CrudRepository<CustomerStoreInfoCache, Integer> {
	List<CustomerStoreInfoCache> findAllByIdIn(List<Integer> idList);
}
