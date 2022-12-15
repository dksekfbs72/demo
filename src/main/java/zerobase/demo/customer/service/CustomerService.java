package zerobase.demo.customer.service;

import zerobase.demo.customer.dto.CustomerStoreInfo;

public interface CustomerService {
	CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.Param param);

}
