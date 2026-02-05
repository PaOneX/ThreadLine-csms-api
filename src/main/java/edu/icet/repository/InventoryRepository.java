package edu.icet.repository;

import edu.icet.model.entity.Inventory;
import edu.icet.model.entity.ProductVariant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    Optional<Inventory> findByVariant(ProductVariant variant);

    Optional<Inventory> findByVariantId(Long variantId);

    boolean existsById(Long id);

    void save(Inventory entity);

    void deleteById(Long id);

    List<Inventory> findAll();

    Optional<Inventory>  findById(Long id);
}
