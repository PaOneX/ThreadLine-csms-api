package edu.icet.repository;

import edu.icet.model.entity.Product;
import edu.icet.model.entity.ProductVariant;

import java.util.List;
import java.util.Optional;

public interface ProductVarientRpository {
    List<ProductVariant> findByColor(String color);

    List<ProductVariant> findBySize(String size);

    List<ProductVariant> findByProduct(Product product);

    Optional<ProductVariant> findByProductId(Long productId);

    Optional<ProductVariant> findById(Long variantId);

    void save(ProductVariant variant);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<ProductVariant> findAll();
}
