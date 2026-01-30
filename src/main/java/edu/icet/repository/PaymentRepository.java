package edu.icet.repository;

import edu.icet.model.entity.PaymentEntity;
import edu.icet.util.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByPaymentMode(PaymentMode paymentMode);
}
