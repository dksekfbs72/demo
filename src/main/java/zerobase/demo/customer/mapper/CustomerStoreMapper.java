package zerobase.demo.customer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import zerobase.demo.customer.dto.CustomerStoreInfo;

@Mapper
public interface CustomerStoreMapper {

    List<CustomerStoreInfo> selectList(CustomerStoreInfo.Param parameter);
    List<CustomerStoreInfo> selectTest(Double userLon);
}
