package edu.icet.mapper;

import edu.icet.model.dto.user.UserDto;
import edu.icet.model.dto.user.UserRequestDto;
import edu.icet.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", source = "roles")
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "roles", source = "roles")
    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> users);
}
