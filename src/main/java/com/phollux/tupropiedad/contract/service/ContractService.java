package com.phollux.tupropiedad.contract.service;

import com.phollux.tupropiedad.contract.model.ContractDTO;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import org.springframework.data.domain.Pageable;


public interface ContractService {

    SimplePage<ContractDTO> findAll(String filter, Pageable pageable);

    ContractDTO get(Integer id);

    Integer create(ContractDTO contractDTO);

    void update(Integer id, ContractDTO contractDTO);

    void delete(Integer id);

}
