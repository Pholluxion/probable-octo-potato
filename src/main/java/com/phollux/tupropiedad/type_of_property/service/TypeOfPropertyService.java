package com.phollux.tupropiedad.type_of_property.service;

import com.phollux.tupropiedad.type_of_property.model.TypeOfPropertyDTO;
import java.util.List;


public interface TypeOfPropertyService {

    List<TypeOfPropertyDTO> findAll();

    TypeOfPropertyDTO get(Integer id);

    Integer create(TypeOfPropertyDTO typeOfPropertyDTO);

    void update(Integer id, TypeOfPropertyDTO typeOfPropertyDTO);

    void delete(Integer id);

}
