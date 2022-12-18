package zerobase.demo.menu.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.demo.common.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	List<Menu> findAllByRestaurantId(Integer storeId);

	Optional<Menu> findById(Integer menuId);
}
