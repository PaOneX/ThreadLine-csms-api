package edu.icet.service.impl;

import edu.icet.mapper.PaymentMapper;
import edu.icet.model.dto.PaymentDto;
import edu.icet.model.dto.PaymentRequestDto;
import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Payment;
import edu.icet.model.enums.PaymentMode;
import edu.icet.repository.InvoiceRepository;
import edu.icet.repository.PaymentRepository;
import edu.icet.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMapper mapper;

    @Override
    @Transactional
    public void createPayment(PaymentRequestDto requestDto) {
        Invoice invoice = invoiceRepository.findById(requestDto.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Payment payment = mapper.toEntity(requestDto);
        payment.setInvoice(invoice);
        repository.save(payment);
    }

    @Override
    @Transactional
    public void updatePayment(Long id, PaymentRequestDto requestDto) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (requestDto.getInvoiceId() != null &&
                !requestDto.getInvoiceId().equals(payment.getInvoice().getId())) {
            Invoice invoice = invoiceRepository.findById(requestDto.getInvoiceId())
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));
            payment.setInvoice(invoice);
        }

        mapper.updateEntityFromDto(requestDto, payment);
        repository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Payment not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPayments() {
        List<Payment> paymentEntities = repository.findAll();
        return mapper.toDtoList(paymentEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentBNyId(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapper.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentMode(PaymentMode paymentMode) {
        List<Payment> payment = repository.findByPaymentMode(paymentMode);
        return mapper.toDtoList(payment);
    }
}
