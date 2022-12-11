package zerobase.demo.owner.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.entity.User;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo.BaseResponse;
import zerobase.demo.owner.dto.UpdateStore;
import zerobase.demo.owner.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class OwnerStoreController {

	private final StoreService storeService;

	//점포 등록
	@PostMapping()
	public CreateStore.Response createStore(@RequestBody CreateStore.Request request, @AuthenticationPrincipal UserDetails loggedInUser) {

		CreateStore dto = CreateStore.fromRequest(request);
		if(loggedInUser != null) dto.setLoggedInUser(loggedInUser);

		return storeService.createStore(dto);
	}

	//점포 열기/닫기
	// @PutMapping("/openclose")
	// public void openCloseStore(@RequestBody OpenCloseStore.Request request) {
	// 	storeService.openCloseStore(OpenCloseStore.fromRequest(request));
	// }
	//
	// //점포 조회
	// @GetMapping()
	// public BaseResponse getStoreByOwnerId(@RequestParam int id) {
	// 	return BaseResponse.fromDtoList(storeService.getStoreInfoByOwnerId(id));
	// }
	//
	// //점포 수정
	// @PutMapping()
	// public void updateStore(@RequestBody UpdateStore.Request request) {
	// 	storeService.updateStore(UpdateStore.fromRequest(request));
	// }
}
