package zerobase.demo.owner.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;
import zerobase.demo.owner.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class StoreController {

	private final StoreService storeService;

	//가게 등록
	@ApiOperation(value = "점주 - 가게 등록", notes = "<p>request는 아래와 같습니다<p>"
		+ "{\n"
		+ "\t\t\"ownerId\" : \"String\"<br>"
		+ "\t\t\"name\" : \"String\"<br>"
		+ "\t\t\"storeAddr\" : \"String\"<br>"
		+ "\t\t\"summary\" : \"String\"<br>"
		+ "\t\t\"pictureUrl\" : \"String\"<br>"
		+ "\t\t\"commission\" : \"Double\"<br>"
		+ "\t\t\"deliveryDistanceKm\" : \"Double\"<br>"
		+ "\t\t\"deliveryTip\" : \"Integer\"<br>"
		+ "\t\t\"lat\" : \"Double\"<br>"
		+ "\t\t\"lon\" : \"Double\"<br>"
		+ "\t}")
	@PostMapping()
	public CreateStore.Response createStore(@RequestBody CreateStore.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		CreateStore dto = CreateStore.fromRequest(request);
		if (loggedInUser != null) {
			dto.setLoggedInUser(loggedInUser);
		}

		return storeService.createStore(dto);
	}

	//가게 열기/닫기
	@ApiOperation(value = "점주 - 점포 열기/닫기", notes = "<p>request는 아래와 같습니다<p>"
		+ "{\n"
		+ "\t\t\"storeId\" : \"Integer\",<br>"
		+ "\t\t\"openClose\" : \"StoreOpenCloseStatus\"<br>"
		+ "\t}")
	@PutMapping("/openclose")
	public OpenCloseStore.Response openCloseStore(@RequestBody OpenCloseStore.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		OpenCloseStore dto = OpenCloseStore.fromRequest(request);
		if (loggedInUser != null) {
			dto.setLoggedInUser(loggedInUser);
		}

		return storeService.openCloseStore(dto);
	}

	//보유 가게 조회
	@ApiOperation("점주 - 보유 점포 정보")
	@ApiImplicitParam(name = "ownerId", value = "점주 아이디")
	@GetMapping()
	public StoreInfo.Response getStoreByOwnerId(@RequestParam String ownerId) {

		return storeService.getStoreInfoByOwnerId(ownerId);
	}

	//점포 수정
	@ApiOperation(value = "점주 - 좀포 정보 수정", notes = "<p>request는 아래와 같습니다<p>"
		+ "{\n"
		+ "\t\t\"storeId\" : \"Integer\"<br>"
		+ "\t\t\"name\" : \"String\"<br>"
		+ "\t\t\"storeAddr\" : \"String\"<br>"
		+ "\t\t\"summary\" : \"String\"<br>"
		+ "\t\t\"pictureUrl\" : \"String\"<br>"
		+ "\t\t\"commission\" : \"Double\"<br>"
		+ "\t\t\"deliveryDistanceKm\" : \"Double\"<br>"
		+ "\t\t\"deliveryTip\" : \"Integer\"<br>"
		+ "\t\t\"lat\" : \"Double\"<br>"
		+ "\t\t\"lon\" : \"Double\"<br>"
		+ "\t}")
	@PutMapping()
	public UpdateStore.Response updateStore(@RequestBody UpdateStore.Request request,
		@AuthenticationPrincipal UserDetails loggedInUser) {

		UpdateStore dto = UpdateStore.fromRequest(request);
		if (loggedInUser != null) {
			dto.setLoggedInUser(loggedInUser);
		}

		return storeService.updateStore(dto);
	}
}
