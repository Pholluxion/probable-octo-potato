package com.phollux.tupropiedad.service.repos;

import com.phollux.tupropiedad.service.domain.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<Service, Long> {

    Page<Service> findAllById(Long id, Pageable pageable);

}
