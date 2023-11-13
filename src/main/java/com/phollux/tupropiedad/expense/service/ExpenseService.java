package com.phollux.tupropiedad.expense.service;

import com.phollux.tupropiedad.expense.model.ExpenseDTO;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import org.springframework.data.domain.Pageable;


public interface ExpenseService {

    SimplePage<ExpenseDTO> findAll(String filter, Pageable pageable);

    ExpenseDTO get(Long id);

    Long create(ExpenseDTO expenseDTO);

    void update(Long id, ExpenseDTO expenseDTO);

    void delete(Long id);

}
