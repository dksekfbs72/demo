package zerobase.demo.customer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import zerobase.demo.customer.dto.CustomerStoreInfo;

public interface CustomerStoreRedisRepository
	extends CrudRepository<List<CustomerStoreInfo>, CustomerStoreInfo.Param> {
}
