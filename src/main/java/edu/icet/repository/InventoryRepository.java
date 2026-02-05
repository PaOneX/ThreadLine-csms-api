package edu.icet.repository;

import edu.icet.model.entity.Inventory;
import edu.icet.model.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByVariant(ProductVariant variant);

    Optional<Inventory> findByVariantId(Long variantId);
}
