package zerobase.demo.owner.service.impl;

import static zerobase.demo.common.type.ResponseCode.*;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.entity.Menu;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.OwnerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.SoldOutStatus;
import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.repository.MenuRepository;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.MenuService;
import zerobase.demo.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final UserRepository userRepository;
	private final StoreRepository storeRepository;

	@Override
	public CreateMenu.Response createMenu(CreateMenu dto) {

		//로그인하지 않은 경우
		if(dto.getLoggedInUser() == null) throw new UserException(ResponseCode.NOT_LOGGED);

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
}
