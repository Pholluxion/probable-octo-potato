package com.phollux.tupropiedad.plan.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.plan.domain.Plan;
import com.phollux.tupropiedad.plan.model.PlanDTO;
import com.phollux.tupropiedad.plan.repos.PlanRepository;
import com.phollux.tupropiedad.product.domain.Product;
import com.phollux.tupropiedad.product.repos.ProductRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final ProductRepository productRepository;

    public PlanServiceImpl(final PlanRepository planRepository,
            final ProductRepository productRepository) {
        this.planRepository = planRepository;
        this.productRepository = productRepository;
    }

    @Override
    public SimplePage<PlanDTO> findAll(final String filter, final Pageable pageable) {
        Page<Plan> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = planRepository.findAllById(longFilter, pageable);
        } else {
            page = planRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(plan -> mapToDTO(plan, new PlanDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public PlanDTO get(final Long id) {
        return planRepository.findById(id)
                .map(plan -> mapToDTO(plan, new PlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final PlanDTO planDTO) {
        final Plan plan = new Plan();
        mapToEntity(planDTO, plan);
        return planRepository.save(plan).getId();
    }

    @Override
    public void update(final Long id, final PlanDTO planDTO) {
        final Plan plan = planRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planDTO, plan);
        planRepository.save(plan);
    }

    @Override
    public void delete(final Long id) {
        planRepository.deleteById(id);
    }

    private PlanDTO mapToDTO(final Plan plan, final PlanDTO planDTO) {
        planDTO.setId(plan.getId());
        planDTO.setName(plan.getName());
        planDTO.setProducts(plan.getProducts().stream()
                .map(product -> product.getId())
                .toList());
        return planDTO;
    }

    private Plan mapToEntity(final PlanDTO planDTO, final Plan plan) {
        plan.setName(planDTO.getName());
        final List<Product> products = productRepository.findAllById(
                planDTO.getProducts() == null ? Collections.emptyList() : planDTO.getProducts());
        if (products.size() != (planDTO.getProducts() == null ? 0 : planDTO.getProducts().size())) {
            throw new NotFoundException("one of products not found");
        }
        plan.setProducts(products.stream().collect(Collectors.toSet()));
        return plan;
    }

    @Override
    public boolean nameExists(final String name) {
        return planRepository.existsByNameIgnoreCase(name);
    }

}
