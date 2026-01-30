package edu.icet.repository;

import edu.icet.model.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

}
