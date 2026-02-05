package edu.icet.repository;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Payment;
import edu.icet.model.enums.PaymentMode;

import java.util.List;

public interface PaymentRepository {
    List<Payment> findByPaymentMode(PaymentMode paymentMode);

    List<Payment> findByInvoice(Invoice invoice);

    List<Payment> findByInvoiceId(Long invoiceId);
}
