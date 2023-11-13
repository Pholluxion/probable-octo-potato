package com.phollux.tupropiedad.income.service;

import com.phollux.tupropiedad.income.model.IncomeDTO;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import org.springframework.data.domain.Pageable;


public interface IncomeService {

    SimplePage<IncomeDTO> findAll(String filter, Pageable pageable);

    IncomeDTO get(Long id);

    Long create(IncomeDTO incomeDTO);

    void update(Long id, IncomeDTO incomeDTO);

    void delete(Long id);

}
