package zerobase.demo.owner.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.dto.DeleteMenu;
import zerobase.demo.owner.dto.MenuInfo;
import zerobase.demo.owner.dto.SetSoldOutStatus;
import zerobase.demo.owner.dto.UpdateMenu;
import zerobase.demo.owner.service.MenuService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {

	private final MenuService menuService;

	//메뉴 등록
	@PostMapping()
	public CreateMenu.Response createMenu(@RequestBody CreateMenu.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		CreateMenu dto = CreateMenu.fromRequest(request);
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return menuService.createMenu(dto);
	}

	// 메뉴 품절/판매중 처리
	@PutMapping("/soldoutstatus")
	public SetSoldOutStatus.Response setSoldOutStatus(@RequestBody SetSoldOutStatus.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		SetSoldOutStatus dto = SetSoldOutStatus.fromRequest(request);
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return menuService.setSoldOutStatus(dto);
	}

	//메뉴 정보 수정
	@PutMapping()
	public UpdateMenu.Response updateMenu(@RequestBody UpdateMenu.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		UpdateMenu dto = UpdateMenu.fromRequest(request);
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return menuService.updateMenu(dto);
	}

	//매장id로 메뉴 조회
	@GetMapping()
	public MenuInfo.Response getMenuInfoByStoreId(@RequestParam Integer storeId) {

		return menuService.getMenuInfoByStoreId(storeId);
	}

	@DeleteMapping()
	public DeleteMenu.Response deleteMenu(@RequestBody DeleteMenu.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		DeleteMenu dto = DeleteMenu.builder()
			.menuId(request.getMenuId())
			.build();
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return menuService.deleteMenu(dto);
	}
}
