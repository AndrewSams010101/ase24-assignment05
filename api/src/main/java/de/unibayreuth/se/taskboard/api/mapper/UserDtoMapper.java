package de.unibayreuth.se.taskboard.api.mapper;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class UserDtoMapper {
    public static final UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    // Mapping from User to UserDto
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    public abstract UserDto fromBusiness(User user);

    // Mapping from UserDto to User
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now(java.time.ZoneId.of(\"UTC\")))")
    public abstract User toBusiness(UserDto userDto);
}