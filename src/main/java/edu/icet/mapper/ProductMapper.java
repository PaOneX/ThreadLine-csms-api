package edu.icet.mapper;

import edu.icet.model.dto.ProductDto;
import edu.icet.model.dto.ProductRequestDto;
import edu.icet.model.entity.Category;
import edu.icet.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "variants", ignore = true)
    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> productList);

    @Mapping(target = "category", ignore = true)
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category.id", source = "category.id")
    @Mapping(target = "category.name", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductDto toDto(ProductRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequestDto requestDto);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "variants", ignore = true)
    void updateEntityFromDto(ProductRequestDto dto, @MappingTarget Product entity);

    /**
     * Maps a request "reference" object into a JPA entity stub.
     * Only the id is set; other fields remain null and should be resolved by the service layer if needed.
     */
    default Category map(edu.icet.model.dto.CategoryRefDto ref) {
        if (ref == null || ref.getId() == null) {
            return null;
        }
        Category c = new Category();
        c.setId(ref.getId());
        return c;
    }
}
