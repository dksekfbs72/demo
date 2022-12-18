package zerobase.demo.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<List<Review>> findByWriterId(Integer writerId);
    Optional<Review> findByOrderId(Integer orderId);

    List<Review> findAllByRestaurantId(Integer storeId);

}
