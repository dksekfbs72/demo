package zerobase.demo.owner.service;

import java.util.List;

import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;

public interface StoreService {

	/**
	 * 신규 점포 추가
	 */
	void createStore(CreateStore.Request request);

	/**
	 * 점포 열기, 닫기
	 */
	void openCloseStore(OpenCloseStore.Request request);

	/**
	 * ownerId로 점포 조회
	 */
	List<StoreInfo> getStoreInfoByOwnerId(int ownerId);
}
