package edu.icet.repository;


import edu.icet.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrderEntity,Long> {
}
