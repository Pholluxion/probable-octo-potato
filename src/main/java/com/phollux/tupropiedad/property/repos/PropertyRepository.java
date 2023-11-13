package com.phollux.tupropiedad.property.repos;

import com.phollux.tupropiedad.property.domain.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PropertyRepository extends JpaRepository<Property, Integer> {

    Page<Property> findAllById(Integer id, Pageable pageable);

}
