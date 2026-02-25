package edu.icet.repository;

import edu.icet.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    boolean existsById(Long id);

    void deleteById(Long id);
}
