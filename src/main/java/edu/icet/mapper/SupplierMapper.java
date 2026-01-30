package edu.icet.mapper;

import edu.icet.model.dto.SupplierDto;
import edu.icet.model.dto.SupplierRequestDto;
import edu.icet.model.entity.SupplierEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierEntity toEntity(SupplierRequestDto supplierRequestDto);

    SupplierDto toDto(SupplierEntity supplierEntity);

    List<SupplierDto> toListDto(List<SupplierEntity> supplierEntities);
}
