package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.ERole;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
