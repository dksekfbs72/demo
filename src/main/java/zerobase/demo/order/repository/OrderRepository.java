package zerobase.demo.order.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.demo.common.entity.Order;

import java.util.List;
import java.util.Optional;
import zerobase.demo.common.type.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(Integer userId);

    Optional<Order> findByUserIdAndStatus(Integer userId, OrderStatus status);
}
