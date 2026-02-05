package edu.icet.repository;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Order;
import edu.icet.model.enums.Status;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    Optional<Invoice> findByOrder(Order order);

    Optional<Invoice> findByOrderId(Long orderId);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByStatus(Status status);
}
