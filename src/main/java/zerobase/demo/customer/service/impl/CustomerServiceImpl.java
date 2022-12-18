package zerobase.demo.customer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.exception.CustomerException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.SelectStoreOpenType;
import zerobase.demo.common.type.Sort;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.customer.mapper.CustomerStoreMapper;
import zerobase.demo.customer.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerStoreMapper customerStoreMapper;
	@Override
	public CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.Param param) {

		Double userLat = param.getUserLat();
		Double userLon = param.getUserLon();

		//필수값 미입력
		if(userLat == null || userLon == null) throw new CustomerException(ResponseCode.BAD_REQUEST);

		//대한민국을 벗어날 경우
		if(userLat < 33.12 || userLat > 38.58
			|| userLon < 125.11 || userLon > 131.86) {
			throw new CustomerException(ResponseCode.BAD_REQUEST);
		}

		//기본값
		if(param.getOffset() == null) param.setOffset(0);
		if(param.getLimit() == null) param.setLimit(50);
		if(param.getOpenType() ==null) param.setOpenType(SelectStoreOpenType.OPEN);
		if(param.getSort() == null) param.setSort(Sort.RANDOM);

		// //좌표 압축
		// //좌표 소수점 3째자리 오차 : 약 110m
		// //소수점 3째자리까지 남기고 반올림
		// userLat = Math.round(userLat*1000)/1000.0;
		// userLon = Math.round(userLon*1000)/1000.0;

		// if((param.getKeyword() == null ||param.getKeyword() == "") && param.getLimit()<=50
		// 	&& param.getOpenType() == SelectStoreOpenType.OPEN) {
		// 	// 이 경우 캐시 조회
		// 	// 캐시 키 : 위,경도
		// }

		List<CustomerStoreInfo> customerStoreInfo = customerStoreMapper.selectList(param);
		System.out.println("********************************************");
		System.out.println("********************************************");
		System.out.println(customerStoreInfo);
		System.out.println("********************************************");
		System.out.println("********************************************");
		return new CustomerStoreInfo.Response(ResponseCode.SELECT_STORE_SUCCESS, customerStoreInfo);
	}
}
