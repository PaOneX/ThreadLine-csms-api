package edu.icet.repository;

import edu.icet.model.entity.ProductVarientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVarientRpository extends JpaRepository<ProductVarientEntity, Long> {
    ProductVarientEntity findProductByColor(String colo);
    ProductVarientEntity findProductBySize(String size);
}
