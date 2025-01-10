package de.unibayreuth.se.taskboard.data.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for persisting users.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByName(String name);
}
