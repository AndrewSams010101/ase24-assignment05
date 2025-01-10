package de.unibayreuth.se.taskboard.api.controller;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.api.mapper.UserDtoMapper;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@OpenAPIDefinition(
        info = @Info(
                title = "TaskBoard",
                version = "0.0.1"
        )
)
@Tag(name = "Users")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    // TODO: Add GET /api/users endpoint to retrieve all users.
    // TODO: Add GET /api/users/{id} endpoint to retrieve a user by ID.
    // TODO: Add POST /api/users endpoint to create a new user based on a provided user DTO.
    private final UserService userService;
    private final UserDtoMapper userDtoMapper = UserDtoMapper.INSTANCE;
    @Operation(
            summary = "Get all users.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = UserDto.class)
                            ),
                            description = "All users as a JSON array."
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers().stream()
                .map(userDtoMapper::fromBusiness)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Get user by ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            ),
                            description = "The user with the provided ID as a JSON object."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No user with the provided ID could be found."
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) throws UserNotFoundException {
        User user = userService.getById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return ResponseEntity.ok(userDtoMapper.fromBusiness(user));
    }

    @Operation(
            summary = "Creates a new user.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            ),
                            description = "The new user as a JSON object."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Malformed request."
                    )
            }
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser (@RequestBody @Valid UserDto userDto) {
        User user = userDtoMapper.toBusiness(userDto);
        User createdUser  = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoMapper.fromBusiness(createdUser ));
    }
}
