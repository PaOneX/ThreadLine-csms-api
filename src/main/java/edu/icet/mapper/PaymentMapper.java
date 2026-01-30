package edu.icet.mapper;

import edu.icet.model.dto.PaymentDto;
import edu.icet.model.dto.PaymentRequestDto;
import edu.icet.model.entity.PaymentEntity;

import java.util.List;

public interface PaymentMapper {
    PaymentEntity toEntity(PaymentRequestDto requestDto);
    PaymentDto toDtoList(PaymentEntity paymentEntity);
    List<PaymentDto> toDtoList(List<PaymentEntity> paymentEntities);
}
