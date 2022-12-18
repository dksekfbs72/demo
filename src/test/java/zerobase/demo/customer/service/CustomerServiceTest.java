package zerobase.demo.customer.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

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

import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.CustomerException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.Result;
import zerobase.demo.common.type.SelectStoreOpenType;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.customer.dto.CustomerStoreDetail;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.owner.dto.CreateMenu;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.repository.MenuRepository;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.MenuService;
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
	@Autowired
	private MenuService menuService;
	@Autowired
	private MenuRepository menuRepository;

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

		createMenu(userName1,storeName1, "메뉴1");
		createMenu(userName1,storeName1, "메뉴2");
		createMenu(userName1,storeName1, "메뉴3");
	}

	@AfterEach
	public void deleteAll() {
		menuRepository.deleteAll();
		storeRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("기본 조회 성공")
	void createMenuSuccess() throws Exception {

		//given
		CustomerStoreInfo.ListParam listParam =
			CustomerStoreInfo.ListParam.builder()
				.userLat(35.0)
				.userLon(130.0)
				.openType(SelectStoreOpenType.ALL)
			.build();

		//when
		CustomerStoreInfo.Response response = customerService.getStoreList(listParam);

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
		CustomerStoreInfo.ListParam listParam =
			CustomerStoreInfo.ListParam.builder()
				.userLat(3500.0)
				.userLon(13000.0)
				.build();


		//when
		CustomerException exception = (CustomerException)assertThrows(RuntimeException.class, () -> {
			customerService.getStoreList(listParam);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), ResponseCode.BAD_REQUEST);
	}

	@Test
	@DisplayName("가게 상세정보 조회 - 성공")
	void getStoreDetail() throws Exception {


		//given
		Integer storeId = storeRepository.findAllByName("narangdStore").get(0).getId();

		CustomerStoreDetail.Request request =
			CustomerStoreDetail.Request.builder()
				.storeId(storeId)
				.userLat(35.0)
				.userLon(130.0)
				.build();

		//when
		CustomerStoreDetail.Response response = customerService.getStoreDetail(request);

		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(response.getCustomerStoreDetail().toString());
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		//then
		assertEquals(response.getCode().getResult(), Result.SUCCESS);
		assertEquals(response.getCode(), ResponseCode.SELECT_STORE_DETAIL_SUCCESS);
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

	private void createMenu(String userId, String storeName, String menuName) {
		UserDetails loggedUser = userService.loadUserByUsername(userId);

		Store store = storeRepository.findAllByName(storeName).get(0);
		int storeId = store.getId();

		CreateMenu dto = CreateMenu.builder()
			.loggedInUser(loggedUser)
			.storeId(storeId)
			.name(menuName)
			.price(1000)
			.pictureUrl("https://naver.com")
			.summary("설명설명")
			.build();

		menuService.createMenu(dto);
	}
}