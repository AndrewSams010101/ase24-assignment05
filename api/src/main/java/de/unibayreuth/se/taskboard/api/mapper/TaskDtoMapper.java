package de.unibayreuth.se.taskboard.api.mapper;

import de.unibayreuth.se.taskboard.api.dtos.TaskDto;
import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.Task;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
@ConditionalOnMissingBean // prevent IntelliJ warning about duplicate beans
@NoArgsConstructor
public abstract class TaskDtoMapper {
    //TODO: Fix this mapper after resolving the other TODOs.
//    protected boolean utcNowUpdated = false;
//    protected LocalDateTime utcNow;

        @Mapping(target = "assignee", expression = "java(mapAssigneeId(source.getAssigneeId()))")
    public abstract TaskDto fromBusiness(Task source);

    @Mapping(target = "assigneeId", expression = "java(mapAssignee(source.getAssignee()))")
    @Mapping(target = "status", source = "status", defaultValue = "TODO")
    @Mapping(target = "createdAt", expression = "java(mapTimestamp(source.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapTimestamp(source.getUpdatedAt()))")
    public abstract Task toBusiness(TaskDto source);

    protected abstract UserDto mapAssigneeId(UUID userId);

    protected abstract UUID mapAssignee(UserDto userDto);

    protected LocalDateTime mapTimestamp (LocalDateTime timestamp) {
        return timestamp;
    }
}

