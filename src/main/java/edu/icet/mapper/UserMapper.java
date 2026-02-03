package edu.icet.mapper;

import edu.icet.model.dto.UserDTO;
import edu.icet.model.dto.UserRequestDto;
import edu.icet.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto userRequestDto);

    List<UserDTO> toDtoList(List<User> userEntity);

    UserDTO toDto(User user);
}
