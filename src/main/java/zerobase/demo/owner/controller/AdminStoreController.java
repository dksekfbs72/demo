package zerobase.demo.owner.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ExampleProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.demo.owner.dto.SetCommission;
import zerobase.demo.owner.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/store")
public class AdminStoreController {

	private final StoreService storeService;

	//수수료 설정
	@ApiOperation(value = "관리 - 수수료 설정", notes = "<p>request는 아래와 같습니다<p>"
		+ "{\n"
		+ "\t\t\"storeId\" : \"Integer\"<br>"
		+ "\t\t\"commission\" : \"Double\"<br>"
		+ "\t}")
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
