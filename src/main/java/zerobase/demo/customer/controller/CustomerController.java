package zerobase.demo.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.customer.service.CustomerService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer")
public class CustomerController {

	private final CustomerService customerService;

	//근처 매장 조회
	@GetMapping("/store")
	public CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.Param param) {

		return customerService.getStoreList(param);
	}


}
