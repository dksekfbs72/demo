package zerobase.demo.owner.service;

import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.dto.DeleteMenu;
import zerobase.demo.owner.dto.MenuInfo;
import zerobase.demo.owner.dto.SetSoldOutStatus;
import zerobase.demo.owner.dto.UpdateMenu;

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

	/**
	 * storeId로 메뉴 목록 조회
	 */
	MenuInfo.Response getMenuInfoByStoreId(Integer storeId);

	/**
	 *메뉴 삭제
	 */
	DeleteMenu.Response deleteMenu(DeleteMenu deleteMenu);
}

