package edu.icet.repository;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Order;
import edu.icet.model.enums.Status;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    Optional<Invoice> findByOrder(Order order);

    Optional<Invoice> findByOrderId(Long orderId);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByStatus(Status status);

    void save(Invoice invoice);

    Optional<Invoice> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Invoice> findAll();
}
