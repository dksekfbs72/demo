package zerobase.demo.owner.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static zerobase.demo.common.type.ResponseCode.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.OwnerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.Result;
import zerobase.demo.common.type.StoreOpenCloseStatus;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.StoreService;
import zerobase.demo.user.repository.UserRepository;
import zerobase.demo.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StoreServiceImplTest {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	MockMvc mockMvc;

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
	@DisplayName("점포 닫기 테스트 - 이미 닫혀 있어서 실패")
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
	@DisplayName("점포 닫기 테스트 - 로그인한 유저와 요청한 유저가 다른 경우")
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

	// @Test
	// @DisplayName("점포 열기 테스트 - AlreadyOpenClosedException")
	// void openCloseAlreadyOpenTest() throws Exception {
	//
	// 	//given
	// 	Integer ownerId = 1; //임시로 user 테이블에 강제 insert
	// 	String name = "특수한 생선가게";
	//
	// 	storeService.createStore(CreateStore.builder()
	// 		.ownerId(ownerId)
	// 		.name(name)
	// 		.build());
	//
	// 	List<Store> storeList = storeRepository.findAllByName(name);
	// 	Store store = storeList.get(0);
	// 	store.setOpenClose(StoreOpenCloseStatus.OPEN);
	// 	storeRepository.save(store);
	//
	// 	String body = mapper.writeValueAsString(
	// 		OpenCloseStore.Request.builder()
	// 			.id(store.getId())
	// 			.openClose(StoreOpenCloseStatus.OPEN)
	// 			.build()
	// 	);
	//
	// 	//when
	// 	ResultActions resultActions = mockMvc.perform(
	// 		MockMvcRequestBuilders.put("/store/openclose")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(body)
	// 	);
	//
	// 	//then
	// 	resultActions.andExpect(
	// 		(rslt) -> assertTrue(rslt.getResolvedException().getClass()
	// 			.isAssignableFrom(AlreadyOpenClosedException.class))
	// 	);
	// }
	//
	// @Test
	// @DisplayName("점포 조회 테스트 - 성공")
	// void getStoreByOwnerIdSuccessTest() throws Exception {
	//
	// 	//given
	// 	Integer ownerId = 1; //임시로 user 테이블에 강제 insert
	//
	//
	// 	//when
	// 	ResultActions resultActions = mockMvc.perform(
	// 		MockMvcRequestBuilders.get("/store?id="+ownerId)
	// 	);
	//
	// 	//then
	// 	resultActions.andExpect(status().isOk());
	// }
}
