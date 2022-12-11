package zerobase.demo.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.demo.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserId(String userId);

	Optional<User> findByEmailAuthKey(String emailAuthKey);


	User findAllByUserId(String userId);
}
