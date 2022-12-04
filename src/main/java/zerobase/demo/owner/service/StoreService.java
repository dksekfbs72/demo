package zerobase.demo.owner.service;

import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;

public interface StoreService {

	/**
	 * 신규 점포 추가
	 */
	void createStore(CreateStore.Request request);

	/**
	 * 점포 열기, 닫기
	 */
	void openCloseStore(OpenCloseStore.Request request);
}
