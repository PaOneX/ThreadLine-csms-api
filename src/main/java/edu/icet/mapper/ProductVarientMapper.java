package edu.icet.mapper;

import edu.icet.model.dto.ProductVarientDto;
import edu.icet.model.dto.ProductvarientRequestDto;
import edu.icet.model.entity.ProductVarientEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductVarientMapper {
    ProductVarientEntity toEntity(ProductvarientRequestDto requestDto);
    ProductVarientDto toDto(ProductVarientEntity productVarientEntity);
    List<ProductVarientDto> toDtoList(List<ProductVarientEntity> productVarientEntityList);
}
