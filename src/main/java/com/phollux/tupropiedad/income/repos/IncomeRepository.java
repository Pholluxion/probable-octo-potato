package com.phollux.tupropiedad.income.repos;

import com.phollux.tupropiedad.income.domain.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IncomeRepository extends JpaRepository<Income, Long> {

    Page<Income> findAllById(Long id, Pageable pageable);

}
