package edu.icet.repository.impl;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Order;
import edu.icet.model.enums.Status;
import edu.icet.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Invoice> invoiceRowMapper = (rs, rowNum) -> {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getLong("id"));
        invoice.setInvoiceNumber(rs.getString("invoice_number"));
        invoice.setTaxAmount(rs.getBigDecimal("tax_amount"));
        invoice.setNetAmount(rs.getBigDecimal("net_amount"));

        String statusStr = rs.getString("status");
        if (statusStr != null) {
            invoice.setStatus(Status.valueOf(statusStr));
        }

        // Set order reference
        Long orderId = rs.getObject("order_id", Long.class);
        if (orderId != null) {
            Order order = new Order();
            order.setId(orderId);
            invoice.setOrder(order);
        }

        Timestamp invoiceDate = rs.getTimestamp("invoice_date");
        if (invoiceDate != null) {
            invoice.setInvoiceDate(invoiceDate.toLocalDateTime());
        }
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            invoice.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            invoice.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return invoice;
    };

    @Override
    public Optional<Invoice> findByOrder(Order order) {
        return findByOrderId(order.getId());
    }

    @Override
    public Optional<Invoice> findByOrderId(Long orderId) {
        String sql = "SELECT * FROM invoices WHERE order_id = ?";
        List<Invoice> invoices = jdbcTemplate.query(sql, invoiceRowMapper, orderId);
        return invoices.isEmpty() ? Optional.empty() : Optional.of(invoices.get(0));
    }

    @Override
    public Optional<Invoice> findByInvoiceNumber(String invoiceNumber) {
        String sql = "SELECT * FROM invoices WHERE invoice_number = ?";
        List<Invoice> invoices = jdbcTemplate.query(sql, invoiceRowMapper, invoiceNumber);
        return invoices.isEmpty() ? Optional.empty() : Optional.of(invoices.get(0));
    }

    @Override
    public List<Invoice> findByStatus(Status status) {
        String sql = "SELECT * FROM invoices WHERE status = ?";
        return jdbcTemplate.query(sql, invoiceRowMapper, status.name());
    }

    @Override
    public void save(Invoice invoice) {
        if (invoice.getId() == null) {
            // INSERT
            invoice.onCreate();
            String sql = "INSERT INTO invoices (order_id, invoice_number, tax_amount, net_amount, invoice_date, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, invoice.getOrder().getId());
                ps.setString(2, invoice.getInvoiceNumber());
                ps.setBigDecimal(3, invoice.getTaxAmount());
                ps.setBigDecimal(4, invoice.getNetAmount());
                ps.setTimestamp(5, Timestamp.valueOf(invoice.getInvoiceDate()));
                ps.setString(6, invoice.getStatus().name());
                ps.setTimestamp(7, Timestamp.valueOf(invoice.getCreatedAt()));
                ps.setTimestamp(8, Timestamp.valueOf(invoice.getUpdatedAt()));
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                invoice.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            invoice.onUpdate();
            String sql = "UPDATE invoices SET order_id = ?, invoice_number = ?, tax_amount = ?, net_amount = ?, invoice_date = ?, status = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    invoice.getOrder().getId(),
                    invoice.getInvoiceNumber(),
                    invoice.getTaxAmount(),
                    invoice.getNetAmount(),
                    Timestamp.valueOf(invoice.getInvoiceDate()),
                    invoice.getStatus().name(),
                    Timestamp.valueOf(invoice.getUpdatedAt()),
                    invoice.getId());
        }
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        String sql = "SELECT * FROM invoices WHERE id = ?";
        List<Invoice> invoices = jdbcTemplate.query(sql, invoiceRowMapper, id);
        return invoices.isEmpty() ? Optional.empty() : Optional.of(invoices.get(0));
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM invoices WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM invoices WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Invoice> findAll() {
        String sql = "SELECT * FROM invoices";
        return jdbcTemplate.query(sql, invoiceRowMapper);
    }
}
