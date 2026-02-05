package edu.icet.repository;

import edu.icet.model.entity.Product;
import edu.icet.model.entity.ProductVariant;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface ProductVarientRpository {
    List<ProductVariant> findByColor(String color);

    List<ProductVariant> findBySize(String size);

    List<ProductVariant> findByProduct(Product product);

    List<ProductVariant> findByProductId(Long productId);

    Optional<ProductVariant> findById(@NotNull(message = "variantId must not be null") Long variantId);
}
