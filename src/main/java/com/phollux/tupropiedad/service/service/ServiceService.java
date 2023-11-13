package com.phollux.tupropiedad.service.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.service.model.ServiceDTO;
import org.springframework.data.domain.Pageable;


public interface ServiceService {

    SimplePage<ServiceDTO> findAll(String filter, Pageable pageable);

    ServiceDTO get(Long id);

    Long create(ServiceDTO serviceDTO);

    void update(Long id, ServiceDTO serviceDTO);

    void delete(Long id);

}
