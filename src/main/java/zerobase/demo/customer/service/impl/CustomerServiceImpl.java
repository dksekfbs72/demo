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

		//캐시에 있으면 캐시에서 읽어서 return 한다


		//캐시에 없으면 db에서 읽어서 캐시에 저장한 후 리턴한다
		List<CustomerStoreInfo> customerStoreInfo = customerStoreMapper.selectList(param);



		return new CustomerStoreInfo.Response(ResponseCode.SELECT_STORE_SUCCESS, customerStoreInfo);
	}
}
