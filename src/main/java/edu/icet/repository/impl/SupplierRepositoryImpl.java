package edu.icet.repository.impl;

import edu.icet.model.entity.Supplier;
import edu.icet.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SupplierRepositoryImpl implements SupplierRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void save(Supplier entity) {

    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Supplier> findAll() {
        return List.of();
    }
}
