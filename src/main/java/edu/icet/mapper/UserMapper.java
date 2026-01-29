package edu.icet.mapper;

import edu.icet.model.dto.UserDTO;
import edu.icet.model.entity.UserEntitty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntitty toEntity(UserDTO userDTO);
    List<UserDTO> toDtoList(List<UserEntitty> userEntity);
    UserDTO toDto(UserEntitty userEntitty);
}
