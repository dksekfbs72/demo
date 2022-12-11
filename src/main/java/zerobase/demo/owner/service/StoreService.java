package zerobase.demo.owner.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;

public interface StoreService {

	/**
	 * 신규 점포 추가
	 */
	CreateStore.Response createStore(CreateStore createStore);

	/**
	 * 점포 열기, 닫기
	 */
	OpenCloseStore.Response openCloseStore(OpenCloseStore openCloseStore);

	/**
	 * ownerId로 점포 조회
	 */
	StoreInfo.Response getStoreInfoByOwnerId(String ownerId);

	/**
	 * 점포 수정
	 */
	UpdateStore.Response updateStore(UpdateStore updateStore);
}
