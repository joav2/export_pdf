package br.com.example.export_data.repository;

import br.com.example.export_data.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}