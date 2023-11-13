package com.phollux.tupropiedad.clause.service;

import com.phollux.tupropiedad.clause.model.ClauseDTO;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import org.springframework.data.domain.Pageable;


public interface ClauseService {

    SimplePage<ClauseDTO> findAll(String filter, Pageable pageable);

    ClauseDTO get(Long id);

    Long create(ClauseDTO clauseDTO);

    void update(Long id, ClauseDTO clauseDTO);

    void delete(Long id);

    boolean nameExists(String name);

}
