package zerobase.demo.owner.service;

import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.SetSoldOutStatus;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateMenu;
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

	/**
	 * 메뉴 수정
	 */
	UpdateMenu.Response updateMenu(UpdateMenu updateMenu);

	// /**
	//  * ownerId로 점포 조회
	//  */
	// StoreInfo.Response getStoreInfoByOwnerId(String ownerId);
}

