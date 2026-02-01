package edu.icet.mapper;

import edu.icet.model.dto.OrderItemDto;
import edu.icet.model.dto.OrderItemRequestDto;
import edu.icet.model.entity.OrderItemEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemEntity toEntity(OrderItemRequestDto requestDto);
    OrderItemDto toDto(OrderItemEntity item);
    List<OrderItemDto> toDtoList(List<OrderItemEntity> items);
}
