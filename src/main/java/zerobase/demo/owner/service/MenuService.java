package zerobase.demo.owner.service;

import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.SetSoldOutStatus;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;

public interface MenuService {

	/**
	 * 메뉴 추가
	 */
	CreateMenu.Response createMenu(CreateMenu createMenu);

	/**
	 * 메뉴 판매중 / 품절 처리
	 */
	SetSoldOutStatus.Response setSoldOutStatus(SetSoldOutStatus setSoldOutStatus);

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
