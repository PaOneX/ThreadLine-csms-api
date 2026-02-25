package edu.icet.repository.impl;

import edu.icet.model.entity.Invoice;
import edu.icet.model.entity.Payment;
import edu.icet.model.enums.PaymentMode;
import edu.icet.repository.PaymentRepository;
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
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Payment> paymentRowMapper = (rs, rowNum) -> {
        Payment payment = new Payment();
        payment.setId(rs.getLong("id"));
        payment.setTransactionId(rs.getString("transaction_id"));
        payment.setAmount(rs.getBigDecimal("amount"));

        String paymentModeStr = rs.getString("payment_mode");
        if (paymentModeStr != null) {
            payment.setPaymentMode(PaymentMode.valueOf(paymentModeStr));
        }

        // Set invoice reference
        Long invoiceId = rs.getObject("invoice_id", Long.class);
        if (invoiceId != null) {
            Invoice invoice = new Invoice();
            invoice.setId(invoiceId);
            payment.setInvoice(invoice);
        }

        Timestamp paymentDate = rs.getTimestamp("payment_date");
        if (paymentDate != null) {
            payment.setPaymentDate(paymentDate.toLocalDateTime());
        }
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            payment.setCreatedAt(createdAt.toLocalDateTime());
        }
        return payment;
    };

    @Override
    public List<Payment> findByPaymentMode(PaymentMode paymentMode) {
        String sql = "SELECT * FROM payments WHERE payment_mode = ?";
        return jdbcTemplate.query(sql, paymentRowMapper, paymentMode.name());
    }

    @Override
    public List<Payment> findByInvoice(Invoice invoice) {
        return findByInvoiceId(invoice.getId());
    }

    @Override
    public List<Payment> findByInvoiceId(Long invoiceId) {
        String sql = "SELECT * FROM payments WHERE invoice_id = ?";
        return jdbcTemplate.query(sql, paymentRowMapper, invoiceId);
    }

    @Override
    public void save(Payment payment) {
        if (payment.getId() == null) {
            // INSERT
            payment.onCreate();
            String sql = "INSERT INTO payments (invoice_id, transaction_id, payment_mode, amount, payment_date, created_at) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, payment.getInvoice().getId());
                ps.setString(2, payment.getTransactionId());
                ps.setString(3, payment.getPaymentMode().name());
                ps.setBigDecimal(4, payment.getAmount());
                ps.setTimestamp(5, Timestamp.valueOf(payment.getPaymentDate()));
                ps.setTimestamp(6, Timestamp.valueOf(payment.getCreatedAt()));
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                payment.setId(keyHolder.getKey().longValue());
            }
        } else {
            // UPDATE
            String sql = "UPDATE payments SET invoice_id = ?, transaction_id = ?, payment_mode = ?, amount = ?, payment_date = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    payment.getInvoice().getId(),
                    payment.getTransactionId(),
                    payment.getPaymentMode().name(),
                    payment.getAmount(),
                    Timestamp.valueOf(payment.getPaymentDate()),
                    payment.getId());
        }
    }

    @Override
    public Optional<Payment> findById(Long id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
        List<Payment> payments = jdbcTemplate.query(sql, paymentRowMapper, id);
        return payments.isEmpty() ? Optional.empty() : Optional.of(payments.get(0));
    }

    @Override
    public List<Payment> findAll() {
        String sql = "SELECT * FROM payments";
        return jdbcTemplate.query(sql, paymentRowMapper);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM payments WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM payments WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
