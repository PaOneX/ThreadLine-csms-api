package edu.icet.controller;

import edu.icet.model.dto.PaymentDto;
import edu.icet.model.dto.PaymentRequestDto;
import edu.icet.service.PaymentService;
import edu.icet.util.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService service;

    @GetMapping
    public List<PaymentDto> getAllPayments() {
        return service.getPayments();
    }

    @GetMapping("/{id}")
    public PaymentDto getPaymentById(@PathVariable Long id) {
        return service.getPaymentBNyId(id);
    }

    @GetMapping("/mode/{paymentMode}")
    public List<PaymentDto> getPaymentsByPaymentMode(@PathVariable PaymentMode paymentMode) {
        return service.getPaymentMode(paymentMode);
    }

    @PostMapping
    public void addPayment(@RequestBody PaymentRequestDto paymentDto) {
        service.createPayment(paymentDto);
    }

    @PutMapping("/{id}")
    public void updatePayment(@PathVariable Long id,@RequestBody PaymentRequestDto paymentDto) {
        service.updatePayment(id, paymentDto);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        service.deletePayment(id);
    }

}
