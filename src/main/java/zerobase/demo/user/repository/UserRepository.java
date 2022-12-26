package zerobase.demo.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.type.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserId(String userId);

	List<User> findAllByStatus(UserStatus userStatus);
	Optional<User> findByEmailAuthKey(String emailAuthKey);

}
