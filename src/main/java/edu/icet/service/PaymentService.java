package edu.icet.service;

import edu.icet.model.dto.PaymentDto;
import edu.icet.model.dto.PaymentRequestDto;
import edu.icet.util.PaymentMode;

import java.util.List;

public interface PaymentService {
    void createPayment(PaymentRequestDto requestDto);
    void updatePayment(Long id,PaymentRequestDto requestDto);
    void deletePayment(Long id);
    List<PaymentDto> getPayments();
    PaymentDto getPaymentBNyId(Long id);
    List<PaymentDto> getPaymentMode(PaymentMode paymentMode);
}
