package edu.icet.repository.impl;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Payment;
import edu.icet.model.enums.PaymentMode;
import edu.icet.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Payment> findByPaymentMode(PaymentMode paymentMode) {
        return List.of();
    }

    @Override
    public List<Payment> findByInvoice(Invoice invoice) {
        return List.of();
    }

    @Override
    public List<Payment> findByInvoiceId(Long invoiceId) {
        return List.of();
    }

    @Override
    public void save(Payment payment) {

    }

    @Override
    public Optional<Payment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Payment> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }
}
