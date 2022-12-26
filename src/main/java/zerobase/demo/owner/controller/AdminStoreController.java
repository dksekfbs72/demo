package zerobase.demo.owner.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.SetCommission;
import zerobase.demo.owner.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/store")
public class AdminStoreController {

	private final StoreService storeService;

	//수수료 설정
	@PostMapping("/commission")
	public SetCommission.Response setCommission(@RequestBody SetCommission.Request request) {

		SetCommission dto =
			SetCommission.builder()
				.storeId(request.getStoreId())
				.commission(request.getCommission())
				.build();

		return storeService.setCommission(dto);
	}
}
