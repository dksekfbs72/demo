package zerobase.demo.customer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.customer.mapper.CustomerStoreMapper;
import zerobase.demo.customer.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerStoreMapper customerStoreMapper;
	@Override
	public CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.Param param) {

		List<CustomerStoreInfo> customerStoreInfo = customerStoreMapper.selectList(param);
		// List<CustomerStoreInfo> customerStoreInfo = customerStoreMapper.selectTest(param.getUserLon());
		System.out.println(customerStoreInfo.size());
		return new CustomerStoreInfo.Response(ResponseCode.SELECT_STORE_SUCCESS, customerStoreInfo);
	}
}
