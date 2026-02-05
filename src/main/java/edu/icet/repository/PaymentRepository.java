package edu.icet.repository;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Payment;
import edu.icet.model.enums.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPaymentMode(PaymentMode paymentMode);

    List<Payment> findByInvoice(Invoice invoice);

    List<Payment> findByInvoiceId(Long invoiceId);
}
