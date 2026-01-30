package edu.icet.mapper;

import edu.icet.model.dto.SupplierDto;
import edu.icet.model.entity.SupplierEntity;

import java.util.List;

public interface SupplierMapper {
    SupplierEntity toEntity(SupplierDto supplierDto);
    SupplierDto toDto(SupplierEntity supplierEntity);
    List<SupplierDto> toListDto(List<SupplierEntity> supplierEntities);
}
