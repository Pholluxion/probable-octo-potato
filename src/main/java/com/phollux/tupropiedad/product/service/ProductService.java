package com.phollux.tupropiedad.product.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.product.model.ProductDTO;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    SimplePage<ProductDTO> findAll(String filter, Pageable pageable);

    ProductDTO get(Long id);

    Long create(ProductDTO productDTO);

    void update(Long id, ProductDTO productDTO);

    void delete(Long id);

    boolean nameExists(String name);

}
