package com.mako.wawel.persistence.repositories.auth;

import com.mako.wawel.entity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
