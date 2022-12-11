package zerobase.demo.owner.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static zerobase.demo.common.type.ResponseCode.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.OwnerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.Result;
import zerobase.demo.common.type.StoreOpenCloseStatus;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.StoreService;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.user.service.UserService;


// @AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StoreServiceImplTest {
	//
	// @Autowired
	// ObjectMapper mapper;
	//
	// @Autowired
	// MockMvc mockMvc;

	@Autowired
	private  UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private StoreService storeService;
	@Autowired
	private StoreRepository storeRepository;

	public void createUser(String userId, UserStatus status) {
		User user = User.builder()
			.userId(userId)
			.status(status)
			.emailAuth(true)
			.password("1234")
			.build();
		userRepository.save(user);
	}

	@BeforeAll
	public void setRegisteredUser() {
		createUser("narangd2083", UserStatus.OWNER);
		createUser("cocacola2083", UserStatus.OWNER);
		createUser("coffee2083", UserStatus.USER);
	}

	@Test
	@DisplayName("점포생성 성공")
	void createStoreSuccess() throws Exception {

		//given
		String userId = "narangd2083";
		UserDetails loggedUser = userService.loadUserByUsername(userId);

		String ownerId = userId;
		String name = "생선가게";
		String storeAddr = "대구 남구";
		String summary = "생선가게입니다.";
		String pictureUrl = "https://naver.com";
		Double commission = 3.5;
		Double deliveryDistanceKm = 5.0;
		Integer deliveryTip = 3000;

		CreateStore dto = CreateStore.builder()
			.loggedInUser(loggedUser)
			.ownerId(ownerId)
			.name(name)
			.storeAddr(storeAddr)
			.summary(summary)
			.pictureUrl(pictureUrl)
			.commission(commission)
			.deliveryDistanceKm(deliveryDistanceKm)
			.deliveryTip(deliveryTip)
			.build();

		//when
		CreateStore.Response response = storeService.createStore(dto);

		//then
		assertEquals(response.getResult(), Result.SUCCESS);
	}

	@Test
	@DisplayName("점포생성 실패 - 요청한 유저가 존재하지 않는경우")
	void createStoreUserNotFound() throws Exception {
		//given
		String userId = "narangd2083";
		UserDetails loggedUser = userService.loadUserByUsername(userId);

		String ownerId = "coca-zero"; // not exist
		String name = "생선가게";
		String storeAddr = "대구 남구";
		String summary = "생선가게입니다.";
		String pictureUrl = "https://naver.com";
		Double commission = 3.5;
		Double deliveryDistanceKm = 5.0;
		Integer deliveryTip = 3000;

		CreateStore dto = CreateStore.builder()
			.loggedInUser(loggedUser)
			.ownerId(ownerId)
			.name(name)
			.storeAddr(storeAddr)
			.summary(summary)
			.pictureUrl(pictureUrl)
			.commission(commission)
			.deliveryDistanceKm(deliveryDistanceKm)
			.deliveryTip(deliveryTip)
			.build();

		//when
		UserException exception = (UserException)assertThrows(RuntimeException.class, () -> {
			storeService.createStore(dto);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), USER_NOT_FIND);
	}

	@Test
	@DisplayName("점포생성 실패 - 로그인한 유저와 요청한 유저가 다른 경우")
	void createStoreNotAuthorized() throws Exception {
		//given
		String userId = "narangd2083";
		UserDetails loggedUser = userService.loadUserByUsername(userId);

		String ownerId = "cocacola2083"; //different from Logged user
		String name = "생선가게";
		String storeAddr = "대구 남구";
		String summary = "생선가게입니다.";
		String pictureUrl = "https://naver.com";
		Double commission = 3.5;
		Double deliveryDistanceKm = 5.0;
		Integer deliveryTip = 3000;

		CreateStore dto = CreateStore.builder()
			.loggedInUser(loggedUser)
			.ownerId(ownerId)
			.name(name)
			.storeAddr(storeAddr)
			.summary(summary)
			.pictureUrl(pictureUrl)
			.commission(commission)
			.deliveryDistanceKm(deliveryDistanceKm)
			.deliveryTip(deliveryTip)
			.build();

		//when
		OwnerException exception = (OwnerException)assertThrows(RuntimeException.class, () -> {
			storeService.createStore(dto);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), NOT_AUTHORIZED);
	}


	Store createStore(String ownerId) {

		UserDetails loggedUser = userService.loadUserByUsername(ownerId);

		String name = "생선가게";
		String storeAddr = "대구 남구";
		String summary = "생선가게입니다.";
		String pictureUrl = "https://naver.com";
		Double commission = 3.5;
		Double deliveryDistanceKm = 5.0;
		Integer deliveryTip = 3000;

		CreateStore dto = CreateStore.builder()
			.loggedInUser(loggedUser)
			.ownerId(ownerId)
			.name(name)
			.storeAddr(storeAddr)
			.summary(summary)
			.pictureUrl(pictureUrl)
			.commission(commission)
			.deliveryDistanceKm(deliveryDistanceKm)
			.deliveryTip(deliveryTip)
			.build();

		storeService.createStore(dto);

		List<Store> storeList = storeRepository.findAllByName("생선가게");
		return storeList.get(0);
	}

	@Test
	@DisplayName("점포 열기 성공")
	void openCloseSuccess() throws Exception {

		//given
		Store store = createStore("narangd2083");

		UserDetails loggedUser = userService.loadUserByUsername("narangd2083");
		StoreOpenCloseStatus newOpenCloseStatus = StoreOpenCloseStatus.OPEN;

		OpenCloseStore dto = OpenCloseStore.builder()
			.storeId(store.getId())
			.openClose(newOpenCloseStatus)
			.build();

		dto.setLoggedInUser(loggedUser);


		//when
		OpenCloseStore.Response response = storeService.openCloseStore(dto);

		//then
		assertEquals(response.getResult(), Result.SUCCESS);
		assertEquals(response.getCode(), OPEN_STORE_SUCCESS);

		//delete from h2
		storeRepository.deleteAll();
	}

	@Test
	@DisplayName("점포 닫기 실패 - 이미 닫혀 있어서 실패")
	void openCloseAlreadyOpened() throws Exception {

		//given
		Store store = createStore("narangd2083");

		UserDetails loggedUser = userService.loadUserByUsername("narangd2083");
		StoreOpenCloseStatus newOpenCloseStatus = StoreOpenCloseStatus.CLOSE;

		OpenCloseStore dto = OpenCloseStore.builder()
			.storeId(store.getId())
			.openClose(newOpenCloseStatus)
			.build();

		dto.setLoggedInUser(loggedUser);

		//when
		OwnerException exception = (OwnerException)assertThrows(RuntimeException.class, () -> {
			storeService.openCloseStore(dto);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), ALREADY_CLOSE);

		//delete from h2
		storeRepository.deleteAll();
	}

	@Test
	@DisplayName("점포 닫기 실패 - 로그인한 유저와 요청한 유저가 다른 경우")
	void openCloseNotAuthorized() throws Exception {

		//given
		Store store = createStore("narangd2083");

		UserDetails loggedUser = userService.loadUserByUsername("cocacola2083");
		StoreOpenCloseStatus newOpenCloseStatus = StoreOpenCloseStatus.CLOSE;

		OpenCloseStore dto = OpenCloseStore.builder()
			.storeId(store.getId())
			.openClose(newOpenCloseStatus)
			.build();

		dto.setLoggedInUser(loggedUser);

		//when
		OwnerException exception = (OwnerException)assertThrows(RuntimeException.class, () -> {
			storeService.openCloseStore(dto);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), NOT_AUTHORIZED);

		//delete from h2
		storeRepository.deleteAll();
	}

	@Test
	@DisplayName("보유 가게 조회 성공")
	void getStoreInfoByOwnerIdSuccess() throws Exception {

		//given
		String ownerId = "narangd2083";
		Store store = createStore(ownerId);

		//when
		StoreInfo.Response response = storeService.getStoreInfoByOwnerId(ownerId);

		//then
		assertEquals(response.getResult(), Result.SUCCESS);
		assertEquals(response.getCode(), SELECT_STORE_SUCCESS);
		assertFalse(response.getStoreInfoList().isEmpty());

		// System.out.println("############################################");
		// System.out.println(response.getStoreInfoList().get(0).getName());

		//delete from h2
		storeRepository.deleteAll();
	}

	@Test
	@DisplayName("보유 가게 조회 실패 - 요청한 유저가 owner가 아닌 경우")
	void getStoreInfoByOwnerIdNotOwner() throws Exception {

		//given
		Store store = createStore("narangd2083");
		String ownerId = "coffee2083";

		//when
		OwnerException exception = (OwnerException)assertThrows(RuntimeException.class, () -> {
			storeService.getStoreInfoByOwnerId(ownerId);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), NOT_OWNER);

		//delete from h2
		storeRepository.deleteAll();
	}

	@Test
	@DisplayName("가게정보 수정 성공")
	void updateStoreSuccess() throws Exception {

		//given
		String userId = "narangd2083";
		Store store = createStore(userId);

		UserDetails loggedUser = userService.loadUserByUsername(userId);

		UpdateStore dto = UpdateStore.builder()
			.storeId(store.getId())
			.loggedInUser(loggedUser)
			.name("과일가게")
			.summary("과일가게 입니다.")
			.build();

		//when
		UpdateStore.Response response = storeService.updateStore(dto);

		//then
		assertEquals(response.getResult(), Result.SUCCESS);

		Optional<Store> optionalStore = storeRepository.findById(store.getId());
		assertEquals(optionalStore.get().getName(), "과일가게");
		assertEquals(optionalStore.get().getSummary(), "과일가게 입니다.");

		//delete from h2
		storeRepository.deleteAll();
	}

	@Test
	@DisplayName("가게정보 수정 실패 - 존재하지 않는 가게")
	void updateStoreNotExistStore() throws Exception {

		//given
		String userId = "narangd2083";
		Store store = createStore(userId);

		UserDetails loggedUser = userService.loadUserByUsername(userId);

		UpdateStore dto = UpdateStore.builder()
			.storeId(99)
			.loggedInUser(loggedUser)
			.name("과일가게")
			.summary("과일가게 입니다.")
			.build();

		//when
		OwnerException exception = (OwnerException)assertThrows(RuntimeException.class, () -> {
			storeService.updateStore(dto);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), STORE_NOT_FOUND);

		//delete from h2
		storeRepository.deleteAll();
	}

	@Test
	@DisplayName("가게정보 수정 실패 - 로그인한 유저가 주인이 아님")
	void updateStoreNotAuthorized() throws Exception {

		//given
		Store store = createStore("narangd2083");

		UserDetails loggedUser = userService.loadUserByUsername("cocacola2083");

		UpdateStore dto = UpdateStore.builder()
			.storeId(store.getId())
			.loggedInUser(loggedUser)
			.name("과일가게")
			.summary("과일가게 입니다.")
			.build();

		//when
		OwnerException exception = (OwnerException)assertThrows(RuntimeException.class, () -> {
			storeService.updateStore(dto);
		});

		//then
		assertEquals(exception.getResponseCode().getResult(), Result.FAIL);
		assertEquals(exception.getResponseCode(), NOT_AUTHORIZED);

		//delete from h2
		storeRepository.deleteAll();
	}
}
