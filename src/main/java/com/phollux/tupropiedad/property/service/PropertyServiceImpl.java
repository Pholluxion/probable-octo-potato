package com.phollux.tupropiedad.property.service;

import com.phollux.tupropiedad.inventory.domain.Inventory;
import com.phollux.tupropiedad.inventory.repos.InventoryRepository;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.property.domain.Property;
import com.phollux.tupropiedad.property.model.PropertyDTO;
import com.phollux.tupropiedad.property.repos.PropertyRepository;
import com.phollux.tupropiedad.type_of_property.domain.TypeOfProperty;
import com.phollux.tupropiedad.type_of_property.repos.TypeOfPropertyRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final TypeOfPropertyRepository typeOfPropertyRepository;
    private final InventoryRepository inventoryRepository;

    public PropertyServiceImpl(final PropertyRepository propertyRepository,
            final TypeOfPropertyRepository typeOfPropertyRepository,
            final InventoryRepository inventoryRepository) {
        this.propertyRepository = propertyRepository;
        this.typeOfPropertyRepository = typeOfPropertyRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public SimplePage<PropertyDTO> findAll(final String filter, final Pageable pageable) {
        Page<Property> page;
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = propertyRepository.findAllById(integerFilter, pageable);
        } else {
            page = propertyRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(property -> mapToDTO(property, new PropertyDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public PropertyDTO get(final Integer id) {
        return propertyRepository.findById(id)
                .map(property -> mapToDTO(property, new PropertyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final PropertyDTO propertyDTO) {
        final Property property = new Property();
        mapToEntity(propertyDTO, property);
        return propertyRepository.save(property).getId();
    }

    @Override
    public void update(final Integer id, final PropertyDTO propertyDTO) {
        final Property property = propertyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(propertyDTO, property);
        propertyRepository.save(property);
    }

    @Override
    public void delete(final Integer id) {
        propertyRepository.deleteById(id);
    }

    private PropertyDTO mapToDTO(final Property property, final PropertyDTO propertyDTO) {
        propertyDTO.setId(property.getId());
        propertyDTO.setName(property.getName());
        propertyDTO.setAddress(property.getAddress());
        propertyDTO.setPrice(property.getPrice());
        propertyDTO.setSquareFeet(property.getSquareFeet());
        propertyDTO.setBedrooms(property.getBedrooms());
        propertyDTO.setBathrooms(property.getBathrooms());
        propertyDTO.setGarage(property.getGarage());
        propertyDTO.setPatio(property.getPatio());
        propertyDTO.setElevator(property.getElevator());
        propertyDTO.setTypeOfProperty(property.getTypeOfProperty() == null ? null : property.getTypeOfProperty().getId());
        propertyDTO.setFurnitureInventory(property.getFurnitureInventory() == null ? null : property.getFurnitureInventory().getId());
        return propertyDTO;
    }

    private Property mapToEntity(final PropertyDTO propertyDTO, final Property property) {
        property.setName(propertyDTO.getName());
        property.setAddress(propertyDTO.getAddress());
        property.setPrice(propertyDTO.getPrice());
        property.setSquareFeet(propertyDTO.getSquareFeet());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setBathrooms(propertyDTO.getBathrooms());
        property.setGarage(propertyDTO.getGarage());
        property.setPatio(propertyDTO.getPatio());
        property.setElevator(propertyDTO.getElevator());
        final TypeOfProperty typeOfProperty = propertyDTO.getTypeOfProperty() == null ? null : typeOfPropertyRepository.findById(propertyDTO.getTypeOfProperty())
                .orElseThrow(() -> new NotFoundException("typeOfProperty not found"));
        property.setTypeOfProperty(typeOfProperty);
        final Inventory furnitureInventory = propertyDTO.getFurnitureInventory() == null ? null : inventoryRepository.findById(propertyDTO.getFurnitureInventory())
                .orElseThrow(() -> new NotFoundException("furnitureInventory not found"));
        property.setFurnitureInventory(furnitureInventory);
        return property;
    }

}
