package main.TaskManager.userService.repositories;

import main.TaskManager.userService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);
}
