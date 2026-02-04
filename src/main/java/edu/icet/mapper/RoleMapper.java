package edu.icet.mapper;

import edu.icet.model.entity.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Set<Role> toEntitySet(Set<String> roleNames);
}
