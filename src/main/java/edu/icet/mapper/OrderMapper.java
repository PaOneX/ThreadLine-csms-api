package edu.icet.mapper;

import edu.icet.model.dto.OrderRequestDto;
import edu.icet.model.dto.OrdersDto;
import edu.icet.model.entity.OrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "orderDate", ignore = true)
    OrderEntity toEntity(OrderRequestDto orderRequestDto);

    OrdersDto toDto(OrderEntity orderEntity);

    List<OrdersDto> toDtoList(List<OrderEntity> orderEntityList);
}
