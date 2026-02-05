package edu.icet.repository.impl;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Order;
import edu.icet.model.enums.Status;
import edu.icet.repository.InvoiceRepository;
import edu.icet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public Optional<Invoice> findByOrder(Order order) {
        return Optional.empty();
    }

    @Override
    public Optional<Invoice> findByOrderId(Long orderId) {
        return Optional.empty();
    }

    @Override
    public Optional<Invoice> findByInvoiceNumber(String invoiceNumber) {
        return Optional.empty();
    }

    @Override
    public List<Invoice> findByStatus(Status status) {
        return List.of();
    }

    @Override
    public void save(Invoice invoice) {

    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Invoice> findAll() {
        return List.of();
    }
}
