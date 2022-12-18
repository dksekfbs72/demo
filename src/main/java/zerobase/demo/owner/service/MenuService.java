package zerobase.demo.owner.service;

import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;

public interface MenuService {

	/**
	 * 메뉴 추가
	 */
	CreateMenu.Response createMenu(CreateMenu createMenu);

	// /**
	//  * 점포 열기, 닫기
	//  */
	// OpenCloseStore.Response openCloseStore(OpenCloseStore openCloseStore);
	//
	// /**
	//  * ownerId로 점포 조회
	//  */
	// StoreInfo.Response getStoreInfoByOwnerId(String ownerId);
	//
	// /**
	//  * 점포 수정
	//  */
	// UpdateStore.Response updateStore(UpdateStore updateStore);
}
