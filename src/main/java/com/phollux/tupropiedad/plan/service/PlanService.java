package com.phollux.tupropiedad.plan.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.plan.model.PlanDTO;
import org.springframework.data.domain.Pageable;


public interface PlanService {

    SimplePage<PlanDTO> findAll(String filter, Pageable pageable);

    PlanDTO get(Long id);

    Long create(PlanDTO planDTO);

    void update(Long id, PlanDTO planDTO);

    void delete(Long id);

    boolean nameExists(String name);

}
