package de.seuhd.campuscoffee.api.mapper;

import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(source = "username", target = "loginName")
    @Mapping(source = "email" , target = "emailAddress")
    @Mapping(source = "created" , target = "createdAt")
    @Mapping(source = "updated", target = "updatedAt")
    UserDto toDto(User user);

    @Mapping(source = "loginName" , target = "username")
    @Mapping(source = "emailAddress" , target = "email")
    @Mapping(target = "created" , ignore = true)
    @Mapping(target = "updated" , ignore = true)
    User toDomain(UserDto userDto);

    List<UserDto> toDtos(List<User> users);
}