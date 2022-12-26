package zerobase.demo.owner.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
	@ApiOperation(value = "점주 - 메뉴 등록", notes = "<p>request는 아래와 같습니다<p>"
		+ "{\n"
		+ "\t\t\"storeId\" : \"Integer\"<br>"
		+ "\t\t\"price\" : \"Integer\"<br>"
		+ "\t\t\"pictureUrl\" : \"String\"<br>"
		+ "\t\t\"name\" : \"String\"<br>"
		+ "\t\t\"summary\" : \"String\"<br>"
		+ "\t}")
	@PostMapping()
	public CreateMenu.Response createMenu(@RequestBody CreateMenu.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		CreateMenu dto = CreateMenu.fromRequest(request);
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return menuService.createMenu(dto);
	}

	// 메뉴 품절/판매중 처리
	@ApiOperation(value = "점주 - 메뉴 품절 처리", notes = "<p>request는 아래와 같습니다<p>"
		+ "{\n"
		+ "\t\t\"menuId\" : \"Integer\"<br>"
		+ "\t\t\"soldOutStatus\" : \"SoldOutStatus\"<br>"
		+ "\t}")
	@PutMapping("/soldoutstatus")
	public SetSoldOutStatus.Response setSoldOutStatus(@RequestBody SetSoldOutStatus.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		SetSoldOutStatus dto = SetSoldOutStatus.fromRequest(request);
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return menuService.setSoldOutStatus(dto);
	}

	//메뉴 정보 수정
	@ApiOperation(value = "점주 - 메뉴 정보 수정", notes = "<p>request는 아래와 같습니다<p>"
		+ "{\n"
		+ "\t\t\"menuId\" : \"Integer\"<br>"
		+ "\t\t\"price\" : \"Integer\"<br>"
		+ "\t\t\"pictureUrl\" : \"String\"<br>"
		+ "\t\t\"name\" : \"String\"<br>"
		+ "\t\t\"summary\" : \"String\"<br>"
		+ "\t}")
	@PutMapping()
	public UpdateMenu.Response updateMenu(@RequestBody UpdateMenu.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		UpdateMenu dto = UpdateMenu.fromRequest(request);
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return menuService.updateMenu(dto);
	}

	//매장id로 메뉴 조회
	@ApiOperation("점주 - 메뉴 조회")
	@ApiImplicitParam(name = "storeId", value = "점포 번호")
	@GetMapping()
	public MenuInfo.Response getMenuInfoByStoreId(@RequestParam Integer storeId) {

		return menuService.getMenuInfoByStoreId(storeId);
	}

	@ApiOperation(value = "점주 - 메뉴 삭제", notes = "<p>request는 아래와 같습니다<p>"
		+ "{\n"
		+ "\t\t\"menuId\" : \"Integer\"<br>"
		+ "\t}")
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
