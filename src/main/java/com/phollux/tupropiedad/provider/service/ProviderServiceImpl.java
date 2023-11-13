package com.phollux.tupropiedad.provider.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.provider.domain.Provider;
import com.phollux.tupropiedad.provider.model.ProviderDTO;
import com.phollux.tupropiedad.provider.repos.ProviderRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(final ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public SimplePage<ProviderDTO> findAll(final String filter, final Pageable pageable) {
        Page<Provider> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = providerRepository.findAllById(longFilter, pageable);
        } else {
            page = providerRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(provider -> mapToDTO(provider, new ProviderDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public ProviderDTO get(final Long id) {
        return providerRepository.findById(id)
                .map(provider -> mapToDTO(provider, new ProviderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ProviderDTO providerDTO) {
        final Provider provider = new Provider();
        mapToEntity(providerDTO, provider);
        return providerRepository.save(provider).getId();
    }

    @Override
    public void update(final Long id, final ProviderDTO providerDTO) {
        final Provider provider = providerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(providerDTO, provider);
        providerRepository.save(provider);
    }

    @Override
    public void delete(final Long id) {
        providerRepository.deleteById(id);
    }

    private ProviderDTO mapToDTO(final Provider provider, final ProviderDTO providerDTO) {
        providerDTO.setId(provider.getId());
        providerDTO.setName(provider.getName());
        providerDTO.setAddress(provider.getAddress());
        providerDTO.setPhone(provider.getPhone());
        providerDTO.setEmail(provider.getEmail());
        providerDTO.setWebsite(provider.getWebsite());
        return providerDTO;
    }

    private Provider mapToEntity(final ProviderDTO providerDTO, final Provider provider) {
        provider.setName(providerDTO.getName());
        provider.setAddress(providerDTO.getAddress());
        provider.setPhone(providerDTO.getPhone());
        provider.setEmail(providerDTO.getEmail());
        provider.setWebsite(providerDTO.getWebsite());
        return provider;
    }

}
