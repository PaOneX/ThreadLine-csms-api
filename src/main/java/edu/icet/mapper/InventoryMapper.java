package edu.icet.mapper;

import edu.icet.model.dto.InventoryDto;
import edu.icet.model.dto.InventoryRequestDto;
import edu.icet.model.entity.Inventory;
import edu.icet.model.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InventoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "lastRestocked", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Inventory toEntity(InventoryRequestDto requestDto);

    @Mapping(target = "variantId", source = "variant.id")
    @Mapping(target = "variantSku", source = "variant.sku")
    @Mapping(target = "productName", source = "variant.product.name")
    InventoryDto toDto(Inventory inventory);

    List<InventoryDto> toDto(List<Inventory> inventoryEntities);

    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(InventoryRequestDto dto, @MappingTarget Inventory entity);

    default ProductVariant mapVariant(Long variantId) {
        if (variantId == null) {
            return null;
        }
        ProductVariant variant = new ProductVariant();
        variant.setId(variantId);
        return variant;
    }
}
