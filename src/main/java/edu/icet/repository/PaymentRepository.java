package edu.icet.repository;

import edu.icet.model.dto.PaymentDto;
import edu.icet.model.entity.PaymentEntity;
import edu.icet.util.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity getPaymentMode(PaymentMode paymentMode);
}
