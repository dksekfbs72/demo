package zerobase.demo.customer.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import zerobase.demo.common.util.Page;
import zerobase.demo.customer.dto.CustomerStoreInfo;

@Mapper
public interface CustomerStoreMapper {

    List<CustomerStoreInfo> selectList(CustomerStoreInfo.ListParam parameter, Page page);
    Optional<CustomerStoreInfo> selectStoreById(CustomerStoreInfo.SelectParam parameter);
}
