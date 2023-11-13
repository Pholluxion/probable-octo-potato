package com.phollux.tupropiedad.product.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.plan.repos.PlanRepository;
import com.phollux.tupropiedad.product.domain.Product;
import com.phollux.tupropiedad.product.model.ProductDTO;
import com.phollux.tupropiedad.product.repos.ProductRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PlanRepository planRepository;

    public ProductServiceImpl(final ProductRepository productRepository,
            final PlanRepository planRepository) {
        this.productRepository = productRepository;
        this.planRepository = planRepository;
    }

    @Override
    public SimplePage<ProductDTO> findAll(final String filter, final Pageable pageable) {
        Page<Product> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = productRepository.findAllById(longFilter, pageable);
        } else {
            page = productRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public ProductDTO get(final Long id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    @Override
    public void update(final Long id, final ProductDTO productDTO) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    @Override
    public void delete(final Long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        planRepository.findAllByProducts(product)
                .forEach(plan -> plan.getProducts().remove(product));
        productRepository.delete(product);
    }

    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setIsEnabled(product.getIsEnabled());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setName(productDTO.getName());
        product.setIsEnabled(productDTO.getIsEnabled());
        return product;
    }

    @Override
    public boolean nameExists(final String name) {
        return productRepository.existsByNameIgnoreCase(name);
    }

}
