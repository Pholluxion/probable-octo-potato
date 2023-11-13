package com.phollux.tupropiedad.inventory.service;

import com.phollux.tupropiedad.inventory.model.InventoryDTO;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import org.springframework.data.domain.Pageable;


public interface InventoryService {

    SimplePage<InventoryDTO> findAll(String filter, Pageable pageable);

    InventoryDTO get(Integer id);

    Integer create(InventoryDTO inventoryDTO);

    void update(Integer id, InventoryDTO inventoryDTO);

    void delete(Integer id);

}
