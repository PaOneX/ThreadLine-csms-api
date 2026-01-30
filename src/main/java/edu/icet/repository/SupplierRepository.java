package edu.icet.repository;

import edu.icet.model.dto.SupplierDto;
import edu.icet.model.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

}
