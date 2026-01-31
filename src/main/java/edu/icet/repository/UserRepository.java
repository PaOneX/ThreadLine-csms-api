package edu.icet.repository;

import edu.icet.model.entity.UserEntitty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntitty, Long> {
}
