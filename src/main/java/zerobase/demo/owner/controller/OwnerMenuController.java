package zerobase.demo.owner.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.service.MenuService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/menu")
public class OwnerMenuController {

	private final MenuService menuService;

	//메뉴 등록
	@PostMapping()
	public CreateMenu.Response createMenu(@RequestBody CreateMenu.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		CreateMenu dto = CreateMenu.fromRequest(request);
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return menuService.createMenu(dto);
	}

	// //가게 열기/닫기
	// @PutMapping("/openclose")
	// public OpenCloseStore.Response openCloseStore(@RequestBody OpenCloseStore.Request request,
	// 	@AuthenticationPrincipal UserDetails loggedInUser) {
	//
	// 	OpenCloseStore dto = OpenCloseStore.fromRequest(request);
	// 	if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);
	//
	// 	return storeService.openCloseStore(OpenCloseStore.fromRequest(request));
	// }
	//
	// //보유 가게 조회
	// @GetMapping()
	// public StoreInfo.Response getStoreByOwnerId(@RequestParam String ownerId) {
	//
	// 	return storeService.getStoreInfoByOwnerId(ownerId);
	// }
	//
	// //점포 수정
	// @PutMapping()
	// public UpdateStore.Response updateStore(@RequestBody UpdateStore.Request request,
	// 	@AuthenticationPrincipal UserDetails loggedInUser) {
	//
	// 	UpdateStore dto = UpdateStore.fromRequest(request);
	// 	if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);
	//
	// 	return storeService.updateStore(UpdateStore.fromRequest(request));
	// }
}
