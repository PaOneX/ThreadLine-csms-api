package edu.icet.repository;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Payment;
import edu.icet.model.enums.PaymentMode;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    List<Payment> findByPaymentMode(PaymentMode paymentMode);

    List<Payment> findByInvoice(Invoice invoice);

    List<Payment> findByInvoiceId(Long invoiceId);

    void save(Payment payment);

    Optional<Payment> findById(Long id);

    List<Payment> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);
}
