package zerobase.demo.coupon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.demo.common.entity.UserCouponTbl;

@Repository
public interface CouponRepository extends JpaRepository<UserCouponTbl,Integer> {
	Optional<UserCouponTbl> findByUserIdAndAndCouponId(Integer userId, Integer couponId);
}
