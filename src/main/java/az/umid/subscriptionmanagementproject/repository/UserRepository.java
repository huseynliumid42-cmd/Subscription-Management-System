package az.umid.subscriptionmanagementproject.repository;

import az.umid.subscriptionmanagementproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u from User  u where lower(u.fullName) like lower(concat('%', :keyword, '%') ) ")
    List<User> searchByName(String keyword);
}
