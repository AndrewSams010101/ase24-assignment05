package de.unibayreuth.se.taskboard.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

//TODO: Add DTO for users.
public record UserDto(
        UUID id,
        @NotNull(message = "Name cannot be null.")
        @NotBlank(message = "Name cannot be blank.")
        String name
) { }
