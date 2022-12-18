package zerobase.demo.customer.service;

import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.customer.dto.CustomerStoreDetail;

public interface CustomerService {
	CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.ListParam listParam);

	CustomerStoreDetail.Response getStoreDetail(CustomerStoreDetail.Request request);

}
