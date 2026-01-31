package edu.icet.mapper;

import edu.icet.model.dto.OrderRequestDto;
import edu.icet.model.dto.OrdersDto;
import edu.icet.model.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderEntity toEntity(OrderRequestDto orderRequestDto);
    OrdersDto toDto(OrderEntity orderEntity);
    List<OrdersDto> toDtoList(List<OrderEntity> orderEntityList);
}
