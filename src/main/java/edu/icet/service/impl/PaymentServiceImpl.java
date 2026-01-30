package edu.icet.service.impl;

import edu.icet.mapper.PaymentMapper;
import edu.icet.model.dto.PaymentDto;
import edu.icet.model.dto.PaymentRequestDto;
import edu.icet.model.entity.PaymentEntity;
import edu.icet.repository.PaymentRepository;
import edu.icet.service.PaymentService;
import edu.icet.util.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    @Override
    public void createPayment(PaymentRequestDto requestDto) {
        repository.save(mapper.toEntity(requestDto));
    }

    @Override
    public void updatePayment(Long id, PaymentRequestDto requestDto) {
        PaymentEntity paymentEntity = repository.findById(id).get();
        paymentEntity.setPaymentMode(requestDto.getPaymentMode());
        paymentEntity.setAmount(requestDto.getAmount());
        paymentEntity.setTransactionId(requestDto.getTransactionId());
        repository.save(paymentEntity);
    }

    @Override
    public void deletePayment(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<PaymentDto> getPayments() {
        List<PaymentEntity> paymentEntities = repository.findAll();
        return mapper.toDtoList(paymentEntities);
    }

    @Override
    public PaymentDto getPaymentBNyId(Long id) {
        PaymentEntity paymentEntity = repository.findById(id).get();
        return mapper.toDto(paymentEntity);
    }

    @Override
    public PaymentDto getPaymentMode(PaymentMode paymentMode) {
        PaymentEntity paymentEntity = repository.getPaymentMode(paymentMode);
        return mapper.toDto(paymentEntity);
    }
}
