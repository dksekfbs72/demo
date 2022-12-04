package zerobase.demo.owner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zerobase.demo.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
