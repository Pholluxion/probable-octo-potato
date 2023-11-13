package com.phollux.tupropiedad.service.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.service.model.ServiceDTO;
import com.phollux.tupropiedad.service.repos.ServiceRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(final ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public SimplePage<ServiceDTO> findAll(final String filter, final Pageable pageable) {
        Page<com.phollux.tupropiedad.service.domain.Service> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = serviceRepository.findAllById(longFilter, pageable);
        } else {
            page = serviceRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(service -> mapToDTO(service, new ServiceDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public ServiceDTO get(final Long id) {
        return serviceRepository.findById(id)
                .map(service -> mapToDTO(service, new ServiceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ServiceDTO serviceDTO) {
        final com.phollux.tupropiedad.service.domain.Service service = new com.phollux.tupropiedad.service.domain.Service();
        mapToEntity(serviceDTO, service);
        return serviceRepository.save(service).getId();
    }

    @Override
    public void update(final Long id, final ServiceDTO serviceDTO) {
        final com.phollux.tupropiedad.service.domain.Service service = serviceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(serviceDTO, service);
        serviceRepository.save(service);
    }

    @Override
    public void delete(final Long id) {
        serviceRepository.deleteById(id);
    }

    private ServiceDTO mapToDTO(final com.phollux.tupropiedad.service.domain.Service service,
            final ServiceDTO serviceDTO) {
        serviceDTO.setId(service.getId());
        serviceDTO.setName(service.getName());
        serviceDTO.setPrice(service.getPrice());
        return serviceDTO;
    }

    private com.phollux.tupropiedad.service.domain.Service mapToEntity(final ServiceDTO serviceDTO,
            final com.phollux.tupropiedad.service.domain.Service service) {
        service.setName(serviceDTO.getName());
        service.setPrice(serviceDTO.getPrice());
        return service;
    }

}
