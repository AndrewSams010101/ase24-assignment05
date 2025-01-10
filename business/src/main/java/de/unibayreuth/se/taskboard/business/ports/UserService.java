package de.unibayreuth.se.taskboard.business.ports;

import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import de.unibayreuth.se.taskboard.business.exceptions.DuplicateNameException;
import org.springframework.lang.NonNull;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface UserService {
    void clear();

    @NonNull
    Optional<User> getById(UUID userId) throws UserNotFoundException;

    @NonNull
    List<User> getAllUsers();

    @NonNull
    User create(User user) throws DuplicateNameException;

    //TODO: Add user service interface that the controller uses to interact with the business layer.
    //TODO: Implement the user service interface in the business layer, using the existing user persistence service.
}
