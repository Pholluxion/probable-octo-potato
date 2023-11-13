package com.phollux.tupropiedad.provider.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.provider.model.ProviderDTO;
import org.springframework.data.domain.Pageable;


public interface ProviderService {

    SimplePage<ProviderDTO> findAll(String filter, Pageable pageable);

    ProviderDTO get(Long id);

    Long create(ProviderDTO providerDTO);

    void update(Long id, ProviderDTO providerDTO);

    void delete(Long id);

}
