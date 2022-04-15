package tech.getarrays.api.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.api.user.bean.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);
}

