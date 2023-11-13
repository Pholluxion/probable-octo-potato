package com.phollux.tupropiedad.expense.repos;

import com.phollux.tupropiedad.expense.domain.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findAllById(Long id, Pageable pageable);

}
