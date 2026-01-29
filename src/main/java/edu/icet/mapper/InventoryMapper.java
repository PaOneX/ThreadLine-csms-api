package edu.icet.mapper;

import edu.icet.model.dto.InventoryDto;
import edu.icet.model.dto.InventoryRequestDto;
import edu.icet.model.entity.InventoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryEntity toEntity(InventoryRequestDto requestDto);
    InventoryDto toDto(InventoryEntity inventoryEntity);
    List<InventoryDto> toDto(List<InventoryEntity> inventoryEntities);
}
