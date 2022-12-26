package zerobase.demo.owner.service.impl;

import static zerobase.demo.common.type.ResponseCode.*;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.entity.Menu;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.exception.CustomerException;
import zerobase.demo.common.exception.OwnerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.SoldOutStatus;
import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.dto.DeleteMenu;
import zerobase.demo.owner.dto.MenuInfo;
import zerobase.demo.owner.dto.SetSoldOutStatus;
import zerobase.demo.owner.dto.UpdateMenu;
import zerobase.demo.owner.repository.MenuRepository;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.MenuService;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

	@Override
	public CreateMenu.Response createMenu(CreateMenu dto) {

		//존재하지 않는 가게인 경우
		Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(() -> new OwnerException(STORE_NOT_FOUND));

		//로그인한 유저가 가게주인이 아닌 경우
		UserDetails loggedInUser = dto.getLoggedInUser();
		if(!loggedInUser.getUsername().equals(store.getUser().getUserId())) throw new OwnerException(NOT_AUTHORIZED);

		Menu newMenu = Menu.fromCreateMenu(dto);
		newMenu.setStore(store);
		newMenu.setSoldOutStatus(SoldOutStatus.SOLD_OUT); //기본값 : 품절
		menuRepository.save(newMenu);

		return new CreateMenu.Response(CREATE_MENU_SUCCESS);
	}

	@Override
	public SetSoldOutStatus.Response setSoldOutStatus(SetSoldOutStatus dto) {
		//로그인하지 않은 경우
		if(dto.getLoggedInUser() == null) throw new UserException(ResponseCode.NOT_LOGGED);

		//존재하지 않는 메뉴인 경우
		Menu menu = menuRepository.findById(dto.getMenuId()).orElseThrow(() -> new OwnerException(MENU_NOT_FOUND));

		//로그인한 유저가 가게주인이 아닌 경우
		if(!dto.getLoggedInUser().getUsername().equals(menu.getStore().getUser().getUserId()))
			throw new OwnerException(NOT_AUTHORIZED);

		//이미 품절인데 품절처리 하려고 하는 경우
		if(menu.getSoldOutStatus() == SoldOutStatus.SOLD_OUT
			&& dto.getSoldOutStatus() == SoldOutStatus.SOLD_OUT)
			throw new OwnerException(ALREADY_SOLD_OUT);

		//이미 판매중인데 판매중으로 바꾸려고 하는 경우
		if(menu.getSoldOutStatus() == SoldOutStatus.FOR_SALE
			&& dto.getSoldOutStatus() == SoldOutStatus.FOR_SALE)
			throw new OwnerException(ALREADY_FOR_SAIL);

		menu.setSoldOutStatus(dto.getSoldOutStatus());
		menuRepository.save(menu);

		return new SetSoldOutStatus.Response(SET_SOLD_OUT_STATUS_SUCCESS);
	}

	@Override
	public UpdateMenu.Response updateMenu(UpdateMenu dto) {
		//로그인하지 않은 경우
		if(dto.getLoggedInUser() == null) throw new UserException(ResponseCode.NOT_LOGGED);

		//존재하지 않는 메뉴인 경우
		Menu menu = menuRepository.findById(dto.getMenuId()).orElseThrow(() -> new OwnerException(MENU_NOT_FOUND));

		//로그인한 유저가 가게주인이 아닌 경우
		if(!dto.getLoggedInUser().getUsername().equals(menu.getStore().getUser().getUserId()))
			throw new OwnerException(NOT_AUTHORIZED);

		menu.setFromUpdateMenu(dto);
		menuRepository.save(menu);

		return new UpdateMenu.Response(UPDATE_MENU_SUCCESS);
	}

	@Override
	public MenuInfo.Response getMenuInfoByStoreId(Integer storeId) {

		//존재하지 않는 가게인 경우
		Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomerException(STORE_NOT_FOUND));

		List<Menu> menuList = menuRepository.findAllByStore(store);

		return new MenuInfo.Response(SELECT_MENU_SUCCESS, menuList);
	}

	@Override
	public DeleteMenu.Response deleteMenu(DeleteMenu dto) {

		//존재하지 않는 메뉴인 경우
		Menu menu = menuRepository.findById(dto.getMenuId()).orElseThrow(() -> new OwnerException(MENU_NOT_FOUND));

		//로그인한 유저가 가게주인이 아닌 경우
		if(!dto.getLoggedInUser().getUsername().equals(menu.getStore().getUser().getUserId()))
			throw new OwnerException(NOT_AUTHORIZED);

		menuRepository.delete(menu);

		return new DeleteMenu.Response(DELETE_MENU_SUCCESS);
	}
}
