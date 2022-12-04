package zerobase.demo.owner.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.service.StoreService;

@RestController
@RequiredArgsConstructor
public class OwnerStoreController {

	private final StoreService storeService;

	//점포 등록
	@PostMapping("/store")
	public void createStore(@RequestBody CreateStore.Request request) {
		storeService.createStore(request);
	}

	//점포 열기

	//점포 닫기

	//배달 가능 거리 설정

	//배달 팁 설정
}
