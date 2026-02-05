package edu.icet.repository;

import edu.icet.model.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository {

    void save(Supplier entity);

    Optional<Supplier> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Supplier> findAll();
}
