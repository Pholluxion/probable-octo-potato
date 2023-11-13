package com.phollux.tupropiedad.product.repos;

import com.phollux.tupropiedad.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllById(Long id, Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

}
