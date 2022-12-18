package zerobase.demo.customer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.exception.CustomerException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.SelectStoreOpenType;
import zerobase.demo.common.type.Sort;
import zerobase.demo.customer.dto.CustomerStoreDetail;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.customer.mapper.CustomerStoreMapper;
import zerobase.demo.customer.service.CustomerService;
import zerobase.demo.owner.dto.MenuInfo;
import zerobase.demo.owner.service.MenuService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerStoreMapper customerStoreMapper;

	private final MenuService menuService;
	@Override
	public CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.ListParam listParam) {

		Double userLat = listParam.getUserLat();
		Double userLon = listParam.getUserLon();

		//필수값 미입력
		if(userLat == null || userLon == null) throw new CustomerException(ResponseCode.BAD_REQUEST);

		//대한민국을 벗어날 경우
		if(userLat < 33.12 || userLat > 38.58
			|| userLon < 125.11 || userLon > 131.86) {
			throw new CustomerException(ResponseCode.BAD_REQUEST);
		}

		//기본값
		if(listParam.getOffset() == null) listParam.setOffset(0);
		if(listParam.getLimit() == null) listParam.setLimit(50);
		if(listParam.getOpenType() ==null) listParam.setOpenType(SelectStoreOpenType.OPEN);
		if(listParam.getSort() == null) listParam.setSort(Sort.RANDOM);

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

		List<CustomerStoreInfo> customerStoreInfo = customerStoreMapper.selectList(listParam);
		return new CustomerStoreInfo.Response(ResponseCode.SELECT_STORE_SUCCESS, customerStoreInfo);
	}

	@Override
	public CustomerStoreDetail.Response getStoreDetail(CustomerStoreDetail.Request request) {

		CustomerStoreInfo customerStoreInfo = getStoreInfo(
			CustomerStoreInfo.SelectParam
				.builder()
				.storeId(request.getStoreId())
				.userLat(request.getUserLat())
				.userLon(request.getUserLon())
				.build()
		);

		MenuInfo.Response response = menuService.getMenuInfoByStoreId(request.getStoreId());

		CustomerStoreDetail customerStoreDetail = CustomerStoreDetail.from(customerStoreInfo, response.getMenuInfoList());

		return new CustomerStoreDetail.Response(ResponseCode.SELECT_STORE_DETAIL_SUCCESS, customerStoreDetail);
	}

	private CustomerStoreInfo getStoreInfo(CustomerStoreInfo.SelectParam param) {

		Double userLat = param.getUserLat();
		Double userLon = param.getUserLon();

		//필수값 미입력
		if(userLat == null || userLon == null) throw new CustomerException(ResponseCode.BAD_REQUEST);

		//대한민국을 벗어날 경우
		if(userLat < 33.12 || userLat > 38.58
			|| userLon < 125.11 || userLon > 131.86) {
			throw new CustomerException(ResponseCode.BAD_REQUEST);
		}
		CustomerStoreInfo customerStoreInfo =  customerStoreMapper.selectStoreById(param)
			.orElseThrow(() -> new CustomerException(ResponseCode.STORE_NOT_FOUND));

		return customerStoreInfo;
	}
}
