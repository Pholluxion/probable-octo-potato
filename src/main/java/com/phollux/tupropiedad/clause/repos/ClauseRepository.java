package com.phollux.tupropiedad.clause.repos;

import com.phollux.tupropiedad.clause.domain.Clause;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClauseRepository extends JpaRepository<Clause, Long> {

    Page<Clause> findAllById(Long id, Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

}
