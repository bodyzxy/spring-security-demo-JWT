package com.example.repository;

import com.example.model.ERole;
import com.example.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author bodyzxy
 * @github https://github.com/bodyzxy
 * @date 2024/5/27 15:09
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
