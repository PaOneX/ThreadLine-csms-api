package edu.icet.repository;

import edu.icet.model.entity.Product;
import edu.icet.model.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVarientRpository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByColor(String color);

    List<ProductVariant> findBySize(String size);

    List<ProductVariant> findByProduct(Product product);

    List<ProductVariant> findByProductId(Long productId);
}
