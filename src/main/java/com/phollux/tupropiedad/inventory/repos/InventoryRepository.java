package com.phollux.tupropiedad.inventory.repos;

import com.phollux.tupropiedad.inventory.domain.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Page<Inventory> findAllById(Integer id, Pageable pageable);

}
