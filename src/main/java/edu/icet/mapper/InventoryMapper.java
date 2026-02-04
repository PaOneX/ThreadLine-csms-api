package edu.icet.mapper;

import edu.icet.model.dto.InventoryDto;
import edu.icet.model.dto.InventoryRequestDto;
import edu.icet.model.entity.InventoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "time", ignore = true)
    InventoryEntity toEntity(InventoryRequestDto requestDto);

    InventoryDto toDto(InventoryEntity inventoryEntity);

    List<InventoryDto> toDto(List<InventoryEntity> inventoryEntities);
}
