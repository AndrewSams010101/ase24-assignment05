package de.unibayreuth.se.taskboard;

import de.unibayreuth.se.taskboard.business.domain.Task;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.ports.TaskService;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Load initial data into the list via the list service from the business layer.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
class LoadInitialData implements InitializingBean {
    private final TaskService taskService;
    private final UserService userService;
    // TODO: Fix this class after resolving the other TODOs.

    @Override
    public void afterPropertiesSet() {
        log.info("Deleting existing data...");
//        userService.clear();
        taskService.clear();

        log.info("Loading initial data...");
        try{
            List<User> users = TestFixtures.createUsers(userService);
            log.info("Loaded {} users.", users.size());

            List<Task> tasks = TestFixtures.createTasks(taskService);
            log.info("Loaded {} tasks.", tasks.size());
            if (!tasks.isEmpty() && !users.isEmpty()) {
                Task task1 = tasks.getFirst();
                task1.setAssigneeId(users.getFirst().getId());
                taskService.upsert(task1);
                log.info("Upserted task: {}", task1);

                Task task2 = tasks.getLast();
                task2.setAssigneeId(users.getLast().getId());
                taskService.upsert(task2);
                log.info("Upserted task: {}", task2);
            } else {
                log.warn("No tasks or users available to load.");
            }
        } catch (Exception e) {
            log.error("Error loading initial data: {}", e.getMessage(), e);
        }

    }
}