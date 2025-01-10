package de.unibayreuth.se.taskboard.business.domain;

import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Domain class that represents a user.
 */
@Data
public class User {
        @Nullable
        private UUID id;
        @NonNull
        private String name;
        private LocalDateTime createdAt;

}
