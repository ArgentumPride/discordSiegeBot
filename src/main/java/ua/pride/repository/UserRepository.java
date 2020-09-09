package ua.pride.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pride.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDisTag(String disTag);
}
