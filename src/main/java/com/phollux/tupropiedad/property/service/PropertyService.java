package com.phollux.tupropiedad.property.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.property.model.PropertyDTO;
import org.springframework.data.domain.Pageable;


public interface PropertyService {

    SimplePage<PropertyDTO> findAll(String filter, Pageable pageable);

    PropertyDTO get(Integer id);

    Integer create(PropertyDTO propertyDTO);

    void update(Integer id, PropertyDTO propertyDTO);

    void delete(Integer id);

}
