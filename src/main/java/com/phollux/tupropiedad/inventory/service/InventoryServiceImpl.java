package com.phollux.tupropiedad.inventory.service;

import com.phollux.tupropiedad.inventory.domain.Inventory;
import com.phollux.tupropiedad.inventory.model.InventoryDTO;
import com.phollux.tupropiedad.inventory.repos.InventoryRepository;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(final InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public SimplePage<InventoryDTO> findAll(final String filter, final Pageable pageable) {
        Page<Inventory> page;
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = inventoryRepository.findAllById(integerFilter, pageable);
        } else {
            page = inventoryRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(inventory -> mapToDTO(inventory, new InventoryDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public InventoryDTO get(final Integer id) {
        return inventoryRepository.findById(id)
                .map(inventory -> mapToDTO(inventory, new InventoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final InventoryDTO inventoryDTO) {
        final Inventory inventory = new Inventory();
        mapToEntity(inventoryDTO, inventory);
        return inventoryRepository.save(inventory).getId();
    }

    @Override
    public void update(final Integer id, final InventoryDTO inventoryDTO) {
        final Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(inventoryDTO, inventory);
        inventoryRepository.save(inventory);
    }

    @Override
    public void delete(final Integer id) {
        inventoryRepository.deleteById(id);
    }

    private InventoryDTO mapToDTO(final Inventory inventory, final InventoryDTO inventoryDTO) {
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setFurnitureType(inventory.getFurnitureType());
        inventoryDTO.setFurnitureName(inventory.getFurnitureName());
        inventoryDTO.setQuantity(inventory.getQuantity());
        inventoryDTO.setCondition(inventory.getCondition());
        return inventoryDTO;
    }

    private Inventory mapToEntity(final InventoryDTO inventoryDTO, final Inventory inventory) {
        inventory.setFurnitureType(inventoryDTO.getFurnitureType());
        inventory.setFurnitureName(inventoryDTO.getFurnitureName());
        inventory.setQuantity(inventoryDTO.getQuantity());
        inventory.setCondition(inventoryDTO.getCondition());
        return inventory;
    }

}
