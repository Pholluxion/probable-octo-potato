package com.phollux.tupropiedad.provider.repos;

import com.phollux.tupropiedad.provider.domain.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Page<Provider> findAllById(Long id, Pageable pageable);

}
