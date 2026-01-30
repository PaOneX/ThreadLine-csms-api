package edu.icet.mapper;

import edu.icet.model.dto.ProductDto;
import edu.icet.model.dto.ProductRequestDto;
import edu.icet.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> productList);

    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category.id", source = "category.id")
    ProductDto toDto(ProductRequestDto requestDto);

    Product toEntity(ProductRequestDto requestDto);

    void updateEntityFromDto(ProductRequestDto dto, @MappingTarget Product entity);
}
