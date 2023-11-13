package com.phollux.tupropiedad.user.repos;

import com.phollux.tupropiedad.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "role")
    User findByEmailIgnoreCase(String email);

    Page<User> findAllById(Long id, Pageable pageable);

    boolean existsByEmailIgnoreCase(String email);

}
