package zerobase.demo.owner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import zerobase.demo.owner.dto.CreateStore;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OwnerStoreTest {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("점포 생성 테스트")
	void createStoreTest() throws Exception {

		//given
		Integer ownerId = 1; //임시로 user 테이블에 강제 insert
		String name = "홍길동";
		String storeAddr = "대구 남구";
		String summary = "테스트점포입니다.";
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
}
