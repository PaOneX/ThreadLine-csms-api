package edu.icet.mapper;

import edu.icet.model.dto.user.UserDto;
import edu.icet.model.dto.user.UserRequestDto;
import edu.icet.model.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto userRequestDto);

    List<UserDto> toDtoList(List<User> userEntity);

    UserDto toDto(User user);

    Page<UserDto> toDtoPage(Page<User> userPage);
}
