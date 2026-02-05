package edu.icet.mapper;

import edu.icet.model.dto.OrderItemDto;
import edu.icet.model.dto.OrderItemRequestDto;
import edu.icet.model.entity.Order;
import edu.icet.model.entity.OrderItem;
import edu.icet.model.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OrderItem toEntity(OrderItemRequestDto requestDto);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "variantId", source = "variant.id")
    @Mapping(target = "productName", source = "variant.product.name")
    @Mapping(target = "variantSize", source = "variant.size")
    @Mapping(target = "variantColor", source = "variant.color")
    OrderItemDto toDto(OrderItem item);

    List<OrderItemDto> toDtoList(List<OrderItem> items);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(OrderItemRequestDto dto, @MappingTarget OrderItem entity);

    default Order mapOrder(Long orderId) {
        if (orderId == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderId);
        return order;
    }

    default ProductVariant mapVariant(Long variantId) {
        if (variantId == null) {
            return null;
        }
        ProductVariant variant = new ProductVariant();
        variant.setId(variantId);
        return variant;
    }
}
