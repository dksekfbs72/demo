package zerobase.demo.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.demo.common.entity.OrderTbl;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderTbl, Integer> {
}
