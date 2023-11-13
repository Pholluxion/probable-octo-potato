package com.phollux.tupropiedad.type_of_property.repos;

import com.phollux.tupropiedad.type_of_property.domain.TypeOfProperty;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeOfPropertyRepository extends JpaRepository<TypeOfProperty, Integer> {
}
