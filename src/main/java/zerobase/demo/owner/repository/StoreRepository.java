package zerobase.demo.owner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	Optional<Store> findById(Integer id);
	List<Store> findAllByName(String name);
	List<Store> findAllByUser(User user);
}
