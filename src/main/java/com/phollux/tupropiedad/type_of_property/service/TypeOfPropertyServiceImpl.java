package com.phollux.tupropiedad.type_of_property.service;

import com.phollux.tupropiedad.type_of_property.domain.TypeOfProperty;
import com.phollux.tupropiedad.type_of_property.model.TypeOfPropertyDTO;
import com.phollux.tupropiedad.type_of_property.repos.TypeOfPropertyRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TypeOfPropertyServiceImpl implements TypeOfPropertyService {

    private final TypeOfPropertyRepository typeOfPropertyRepository;

    public TypeOfPropertyServiceImpl(final TypeOfPropertyRepository typeOfPropertyRepository) {
        this.typeOfPropertyRepository = typeOfPropertyRepository;
    }

    @Override
    public List<TypeOfPropertyDTO> findAll() {
        final List<TypeOfProperty> typeOfProperties = typeOfPropertyRepository.findAll(Sort.by("id"));
        return typeOfProperties.stream()
                .map(typeOfProperty -> mapToDTO(typeOfProperty, new TypeOfPropertyDTO()))
                .toList();
    }

    @Override
    public TypeOfPropertyDTO get(final Integer id) {
        return typeOfPropertyRepository.findById(id)
                .map(typeOfProperty -> mapToDTO(typeOfProperty, new TypeOfPropertyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final TypeOfPropertyDTO typeOfPropertyDTO) {
        final TypeOfProperty typeOfProperty = new TypeOfProperty();
        mapToEntity(typeOfPropertyDTO, typeOfProperty);
        return typeOfPropertyRepository.save(typeOfProperty).getId();
    }

    @Override
    public void update(final Integer id, final TypeOfPropertyDTO typeOfPropertyDTO) {
        final TypeOfProperty typeOfProperty = typeOfPropertyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(typeOfPropertyDTO, typeOfProperty);
        typeOfPropertyRepository.save(typeOfProperty);
    }

    @Override
    public void delete(final Integer id) {
        typeOfPropertyRepository.deleteById(id);
    }

    private TypeOfPropertyDTO mapToDTO(final TypeOfProperty typeOfProperty,
            final TypeOfPropertyDTO typeOfPropertyDTO) {
        typeOfPropertyDTO.setId(typeOfProperty.getId());
        typeOfPropertyDTO.setName(typeOfProperty.getName());
        typeOfPropertyDTO.setDescription(typeOfProperty.getDescription());
        return typeOfPropertyDTO;
    }

    private TypeOfProperty mapToEntity(final TypeOfPropertyDTO typeOfPropertyDTO,
            final TypeOfProperty typeOfProperty) {
        typeOfProperty.setName(typeOfPropertyDTO.getName());
        typeOfProperty.setDescription(typeOfPropertyDTO.getDescription());
        return typeOfProperty;
    }

}
