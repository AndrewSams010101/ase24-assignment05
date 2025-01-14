package de.unibayreuth.se.taskboard;

import de.unibayreuth.se.taskboard.api.dtos.TaskDto;
import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.api.mapper.TaskDtoMapper;
import de.unibayreuth.se.taskboard.business.domain.Task;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class TaskBoardSystemTests extends AbstractSystemTest {

    @Autowired
    private TaskDtoMapper taskDtoMapper;
    @Autowired
    private final UserService userService;

    public TaskBoardSystemTests(UserService userService) {
        this.userService = userService;
    }

    @Test
    void getAllCreatedTasks() {
        List<Task> createdTasks = TestFixtures.createTasks(taskService);

        List<Task> retrievedTasks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/tasks")
                .then()
                .statusCode(200)
                .body(".", hasSize(createdTasks.size()))
                .and()
                .extract().jsonPath().getList("$", TaskDto.class)
                .stream()
                .map(taskDtoMapper::toBusiness)
                .toList();

        assertThat(retrievedTasks)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt", "updatedAt") // prevent issues due to differing timestamps after conversions
                .containsExactlyInAnyOrderElementsOf(createdTasks);
    }

    @Test
    void createAndDeleteTask() {
        Task createdTask = taskService.create(
                TestFixtures.getTasks().getFirst()
        );

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .delete("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(400);
    }
    @Test
    void createUser () {
        // Create a UserDto to send in the request
        UserDto newUserDto = new UserDto(null, "Alice");

        // Send a POST request to create a new user
        UserDto createdUserDto = given()
                .contentType(ContentType.JSON)
                .body(newUserDto)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201) // Expecting 201 Created
                .extract().as(UserDto.class);

        // Assert that the created user matches the input
        assertThat(createdUserDto.name()).isEqualTo(newUserDto.name());
    }

    @Test
    void getUserById() {
        // Create a user
        User createdUser  = userService.create(TestFixtures.getUsers().getFirst());

        // Retrieve the user by ID
        UserDto retrievedUserDto = when()
                .get("/api/users/{id}", createdUser .getId())
                .then()
                .statusCode(200)
                .extract().as(UserDto.class);

        // Assert that the retrieved user matches the created user
        assertThat(retrievedUserDto.name()).isEqualTo(createdUser.getName());
    }

    @Test
    void deleteUser () {
        // Create a user
        User createdUser  = userService.create(TestFixtures.getUsers().getFirst());

        // Delete the user
        when()
                .delete("/api/users/{id}", createdUser.getId())
                .then()
                .statusCode(200);

        // Verify that the user cannot be retrieved after deletion
        when()
                .get("/api/users/{id}", createdUser.getId())
                .then()
                .statusCode(404); // Expecting 404 Not Found after deletion
    }
        //TODO: Add at least one test for each new endpoint in the users controller (the create endpoint can be tested as part of the other endpoints).
}