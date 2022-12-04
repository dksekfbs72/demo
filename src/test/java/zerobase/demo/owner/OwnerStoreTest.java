package zerobase.demo.owner;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import zerobase.demo.entity.Store;
import zerobase.demo.exception.AlreadyOpenClosedException;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.StoreService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OwnerStoreTest {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreRepository storeRepository;

	@Test
	@DisplayName("점포생성 테스트 - 성공")
	void createStoreSuccessTest() throws Exception {

		//given
		Integer ownerId = 1; //임시로 user 테이블에 강제 insert
		String name = "생선가게";
		String storeAddr = "대구 남구";
		String summary = "생선가게입니다.";
		String pictureUrl = "https://naver.com";
		Double commission = 3.5;
		Double deliveryDistanceKm = 5.0;
		Integer deliveryTip = 3000;

		String body = mapper.writeValueAsString(
			CreateStore.Request.builder()
				.ownerId(ownerId)
				.name(name)
				.storeAddr(storeAddr)
				.summary(summary)
				.pictureUrl(pictureUrl)
				.commission(commission)
				.deliveryDistanceKm(deliveryDistanceKm)
				.deliveryTip(deliveryTip)
				.build()
		);

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post("/store")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
		);

		//then
		resultActions.andExpect(status().isOk());
	}

	@Test
	@DisplayName("점포 열기 테스트 - 성공")
	void openCloseSuccessTest() throws Exception {

		//given
		Integer ownerId = 1; //임시로 user 테이블에 강제 insert
		String name = "특수한 생선가게";

		storeService.createStore(CreateStore.Request.builder()
				.ownerId(ownerId)
				.name(name)
				.build());

		List<Store> storeList = storeRepository.findAllByName(name);
		Store store = storeList.get(0);

		String body = mapper.writeValueAsString(
			OpenCloseStore.Request.builder()
				.id(store.getId())
				.openClose(true)
				.build()
		);

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.put("/store/update/openclose")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
		);

		//then
		resultActions.andExpect(status().isOk());
		storeList = storeRepository.findAllByName(name);
		store = storeList.get(0);
		assertEquals(store.getOpenClose(), true);
	}

	@Test
	@DisplayName("점포 열기 테스트 - AlreadyOpenClosedException")
	void openCloseAlreadyOpenTest() throws Exception {

		//given
		Integer ownerId = 1; //임시로 user 테이블에 강제 insert
		String name = "특수한 생선가게";

		storeService.createStore(CreateStore.Request.builder()
			.ownerId(ownerId)
			.name(name)
			.build());

		List<Store> storeList = storeRepository.findAllByName(name);
		Store store = storeList.get(0);
		store.setOpenClose(true);
		storeRepository.save(store);

		String body = mapper.writeValueAsString(
			OpenCloseStore.Request.builder()
				.id(store.getId())
				.openClose(true)
				.build()
		);

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.put("/store/update/openclose")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
		);

		//then
		resultActions.andExpect(
			(rslt) -> assertTrue(rslt.getResolvedException().getClass()
				.isAssignableFrom(AlreadyOpenClosedException.class))
		);


	}
}
