package zerobase.demo.customer.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.CustomerException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.Result;
import zerobase.demo.common.type.SelectStoreOpenType;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.StoreService;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.user.service.UserService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Transactional
class CustomerServiceTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private CustomerService customerService;

	@BeforeEach
	public void setUp() {
		String userName1 = "narangd2083";
		String userName2 = "cola2083";
		String storeName1 = "narangdStore";
		String storeName2 = "colaStore";

		createUser(userName1, UserStatus.OWNER);
		createUser(userName2, UserStatus.OWNER);

		createStore(userName1, storeName1);
		createStore(userName2, storeName2);
	}

	@AfterEach
	public void deleteAll() {
		storeRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("기본 조회 성공")
	void createMenuSuccess() throws Exception {

		//given
		CustomerStoreInfo.Param param =
			CustomerStoreInfo.Param.builder()
				.userLat(35.0)
				.userLon(130.0)
				.openType(SelectStoreOpenType.ALL)
			.build();

		//when
		CustomerStoreInfo.Response response = customerService.getStoreList(param);

		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// for(CustomerStoreInfo x : response.getList()) {
		// 	System.out.println(x.getName());
		// }
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		//then
		assertEquals(response.getCode().getResult(), Result.SUCCESS);
		assertEquals(response.getCode(), ResponseCode.SELECT_STORE_SUCCESS);
		assertEquals(response.getList().size(), 2);
	}

	@Test
	@DisplayName("기본 조회 실패 - 좌표 한국 밖")
	void createMenuBadRequest() throws Exception {

		//given
		CustomerStoreInfo.Param param =
			CustomerStoreInfo.Param.builder()
				.userLat(3500.0)
				.userLon(13000.0)
				.build();


		//when
		CustomerException exception = (CustomerException)assertThrows(RuntimeException.class, () -> {
			customerService.getStoreList(param);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), ResponseCode.BAD_REQUEST);
	}



	private void createUser(String userId, UserStatus status) {
		User user = User.builder()
			.userId(userId)
			.status(status)
			.emailAuth(true)
			.password("1234")
			.build();
		userRepository.save(user);
	}

	private void createStore(String ownerId, String storeName) {

		UserDetails loggedUser = userService.loadUserByUsername(ownerId);

		String storeAddr = "대구 남구";
		String summary = "서머리서머리";
		String pictureUrl = "https://naver.com";
		Double commission = 3.5;
		Double deliveryDistanceKm = 5.0;
		Integer deliveryTip = 3000;
		Double lat = 123.4545;
		Double lon = 92.332;

		CreateStore dto = CreateStore.builder()
			.loggedInUser(loggedUser)
			.ownerId(ownerId)
			.name(storeName)
			.storeAddr(storeAddr)
			.summary(summary)
			.pictureUrl(pictureUrl)
			.commission(commission)
			.deliveryDistanceKm(deliveryDistanceKm)
			.deliveryTip(deliveryTip)
			.lat(lat)
			.lon(lon)
			.build();

		storeService.createStore(dto);
	}
}