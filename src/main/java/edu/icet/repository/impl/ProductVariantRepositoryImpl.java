package edu.icet.repository.impl;

import edu.icet.model.entity.Product;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.ProductVarientRpository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductVariantRepositoryImpl implements ProductVarientRpository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<ProductVariant> findByColor(String color) {
        return List.of();
    }

    @Override
    public List<ProductVariant> findBySize(String size) {
        return List.of();
    }

    @Override
    public List<ProductVariant> findByProduct(Product product) {
        return List.of();
    }

    @Override
    public Optional<ProductVariant> findByProductId(Long productId) {
        return null;
    }

    @Override
    public Optional<ProductVariant> findById(Long variantId) {
        return Optional.empty();
    }

    @Override
    public void save(ProductVariant variant) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ProductVariant> findAll() {
        return List.of();
    }
}
