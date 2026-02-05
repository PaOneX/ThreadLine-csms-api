package edu.icet.repository.impl;

import edu.icet.model.entity.Inventory;
import edu.icet.model.entity.ProductVariant;
import edu.icet.repository.InventoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {
    @Override
    public Optional<Inventory> findByVariant(ProductVariant variant) {
        return Optional.empty();
    }

    @Override
    public Optional<Inventory> findByVariantId(Long variantId) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void save(Inventory entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Inventory> findAll() {
        return List.of();
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return Optional.empty();
    }
}
