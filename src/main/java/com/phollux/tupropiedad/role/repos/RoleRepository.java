package com.phollux.tupropiedad.role.repos;

import com.phollux.tupropiedad.role.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    boolean existsByNameIgnoreCase(String name);

}
