package zerobase.demo.owner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;
import zerobase.demo.owner.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class OwnerStoreController {

	private final StoreService storeService;

	//점포 등록
	@PostMapping()
	public void createStore(@RequestBody CreateStore.Request request) {
		storeService.createStore(CreateStore.fromRequest(request));
	}

	//점포 열기/닫기
	@PutMapping("/openclose")
	public void openCloseStore(@RequestBody OpenCloseStore.Request request) {
		storeService.openCloseStore(OpenCloseStore.fromRequest(request));
	}

	//점포 조회
	@GetMapping()
	public StoreInfo.Response getStoreByOwnerId(@RequestParam int id) {
		return StoreInfo.Response.fromDtoList(storeService.getStoreInfoByOwnerId(id));
	}

	//점포 수정
	@PutMapping()
	public void updateStore(@RequestBody UpdateStore.Request request) {
		storeService.updateStore(UpdateStore.fromRequest(request));
	}
}
