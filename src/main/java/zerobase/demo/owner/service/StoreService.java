package zerobase.demo.owner.service;

import zerobase.demo.owner.dto.CreateStore;

public interface StoreService {

	/**
	 * 신규 점포 추가
	 */
	void createStore(CreateStore.Request request);

}
