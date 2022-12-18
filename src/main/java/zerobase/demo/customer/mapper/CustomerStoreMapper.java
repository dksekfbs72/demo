package zerobase.demo.customer.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import zerobase.demo.customer.dto.CustomerStoreInfo;

@Mapper
public interface CustomerStoreMapper {

    List<CustomerStoreInfo> selectList(CustomerStoreInfo.ListParam parameter);
    Optional<CustomerStoreInfo> selectStoreById(CustomerStoreInfo.SelectParam parameter);
}
