package com.phollux.tupropiedad.plan.repos;

import com.phollux.tupropiedad.plan.domain.Plan;
import com.phollux.tupropiedad.product.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanRepository extends JpaRepository<Plan, Long> {

    Page<Plan> findAllById(Long id, Pageable pageable);

    List<Plan> findAllByProducts(Product product);

    boolean existsByNameIgnoreCase(String name);

}
