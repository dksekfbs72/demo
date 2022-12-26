package zerobase.demo.owner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zerobase.demo.common.entity.Menu;
import zerobase.demo.common.entity.Store;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	List<Menu> findAllByName(String name);
	List<Menu> findAllByStore(Store store);

	//List<Menu> findAllByRestaurantId(Integer storeId);

	Optional<Menu> findById(Integer menuId);
}
