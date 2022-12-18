package zerobase.demo.owner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zerobase.demo.common.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
