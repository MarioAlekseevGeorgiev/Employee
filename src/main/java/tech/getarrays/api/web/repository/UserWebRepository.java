package tech.getarrays.api.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.getarrays.api.web.entity.UserWeb;

@Repository
public interface UserWebRepository extends JpaRepository<UserWeb, Long> {
    UserWeb findUserWebByName(String username);
}
