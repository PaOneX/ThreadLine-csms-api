package edu.icet.mapper;

import edu.icet.model.dto.SupplierDto;
import edu.icet.model.dto.SupplierRequestDto;
import edu.icet.model.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupplierMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Supplier toEntity(SupplierRequestDto supplierRequestDto);

    SupplierDto toDto(Supplier supplier);

    List<SupplierDto> toListDto(List<Supplier> supplierEntities);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(SupplierRequestDto dto, @MappingTarget Supplier entity);
}
