package zerobase.demo.owner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import zerobase.demo.DemoApplication;
import zerobase.demo.common.entity.Store;
import zerobase.demo.owner.repository.StoreRepository;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class H2Test {

	@Autowired
	StoreRepository storeRepository;

	@Test
	public void test() {
		System.out.println("test");

		Store newStore = Store.builder()
			.id(1)
			.name("test")
			.build();

		storeRepository.save(newStore);
		List<Store> storeList = storeRepository.findAllByName("test");

		assertEquals(storeList.get(0).getName(), "test");
	}
}
