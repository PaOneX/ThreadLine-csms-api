package edu.icet.mapper;

import edu.icet.model.dto.PaymentDto;
import edu.icet.model.dto.PaymentRequestDto;
import edu.icet.model.entity.PaymentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "payDate", ignore = true)
    PaymentEntity toEntity(PaymentRequestDto requestDto);

    PaymentDto toDto(PaymentEntity paymentEntity);

    List<PaymentDto> toDtoList(List<PaymentEntity> paymentEntities);
}
