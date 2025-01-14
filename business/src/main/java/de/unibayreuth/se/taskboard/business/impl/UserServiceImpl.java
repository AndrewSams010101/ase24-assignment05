package de.unibayreuth.se.taskboard.business.impl;

import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.DuplicateNameException;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import de.unibayreuth.se.taskboard.business.ports.UserPersistenceService; // Assuming you have a UserRepository
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserPersistenceService userPersistenceService;

    @Override
    public void clear() {
        userPersistenceService.clear(); // Clear all users from the repository
    }
    @Override
    @NonNull
    public Optional<User> getById(UUID userId) {
        return Optional.ofNullable(userPersistenceService.getById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId)));
        //.orElseThrow(() -> new UserNotFoundException("User  not found with ID: " + userId));
    }

    @Override
    @NonNull
    public List<User> getAllUsers() {
        return userPersistenceService.getAll();
    }

    @Override
    @NonNull
    public User create(User user) throws DuplicateNameException {
        return userPersistenceService.upsert(user);
    }
}