package edu.icet.repository;

import edu.icet.model.entity.ProductVarientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductVarientRpository extends JpaRepository<ProductVarientEntity, Long> {
    List<ProductVarientEntity> findProductByColor(String colo);
    List<ProductVarientEntity> findProductBySize(String size);
}
