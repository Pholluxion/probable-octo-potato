package com.phollux.tupropiedad.contract.service;

import com.phollux.tupropiedad.clause.domain.Clause;
import com.phollux.tupropiedad.clause.repos.ClauseRepository;
import com.phollux.tupropiedad.contract.domain.Contract;
import com.phollux.tupropiedad.contract.model.ContractDTO;
import com.phollux.tupropiedad.contract.repos.ContractRepository;
import com.phollux.tupropiedad.image.domain.Image;
import com.phollux.tupropiedad.image.repos.ImageRepository;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.property.domain.Property;
import com.phollux.tupropiedad.property.repos.PropertyRepository;
import com.phollux.tupropiedad.user.domain.User;
import com.phollux.tupropiedad.user.repos.UserRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final ClauseRepository clauseRepository;
    private final ImageRepository imageRepository;

    public ContractServiceImpl(final ContractRepository contractRepository,
            final UserRepository userRepository, final PropertyRepository propertyRepository,
            final ClauseRepository clauseRepository, final ImageRepository imageRepository) {
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.clauseRepository = clauseRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public SimplePage<ContractDTO> findAll(final String filter, final Pageable pageable) {
        Page<Contract> page;
        if (filter != null) {
            Integer integerFilter = null;
            try {
                integerFilter = Integer.parseInt(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = contractRepository.findAllById(integerFilter, pageable);
        } else {
            page = contractRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(contract -> mapToDTO(contract, new ContractDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public ContractDTO get(final Integer id) {
        return contractRepository.findById(id)
                .map(contract -> mapToDTO(contract, new ContractDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final ContractDTO contractDTO) {
        final Contract contract = new Contract();
        mapToEntity(contractDTO, contract);
        return contractRepository.save(contract).getId();
    }

    @Override
    public void update(final Integer id, final ContractDTO contractDTO) {
        final Contract contract = contractRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(contractDTO, contract);
        contractRepository.save(contract);
    }

    @Override
    public void delete(final Integer id) {
        contractRepository.deleteById(id);
    }

    private ContractDTO mapToDTO(final Contract contract, final ContractDTO contractDTO) {
        contractDTO.setId(contract.getId());
        contractDTO.setStartDate(contract.getStartDate());
        contractDTO.setEndDate(contract.getEndDate());
        contractDTO.setRent(contract.getRent());
        contractDTO.setDeposit(contract.getDeposit());
        contractDTO.setStatus(contract.getStatus());
        contractDTO.setTenant(contract.getTenant() == null ? null : contract.getTenant().getId());
        contractDTO.setLandlord(contract.getLandlord() == null ? null : contract.getLandlord().getId());
        contractDTO.setClauses(contract.getClauses().stream()
                .map(Clause::getId)
                .toList());
        contractDTO.setImages(contract.getImages().stream()
                .map(Image::getId)
                .toList());
        return contractDTO;
    }

    private Contract mapToEntity(final ContractDTO contractDTO, final Contract contract) {
        contract.setStartDate(contractDTO.getStartDate());
        contract.setEndDate(contractDTO.getEndDate());
        contract.setRent(contractDTO.getRent());
        contract.setDeposit(contractDTO.getDeposit());
        contract.setStatus(contractDTO.getStatus());
        final User tenant = contractDTO.getTenant() == null ? null : userRepository.findById(contractDTO.getTenant())
                .orElseThrow(() -> new NotFoundException("tenant not found"));
        contract.setTenant(tenant);
        final Property landlord = contractDTO.getLandlord() == null ? null : propertyRepository.findById(contractDTO.getLandlord())
                .orElseThrow(() -> new NotFoundException("landlord not found"));
        contract.setLandlord(landlord);
        final List<Clause> clauses = clauseRepository.findAllById(
                contractDTO.getClauses() == null ? Collections.emptyList() : contractDTO.getClauses());
        if (clauses.size() != (contractDTO.getClauses() == null ? 0 : contractDTO.getClauses().size())) {
            throw new NotFoundException("one of clauses not found");
        }
        contract.setClauses(new HashSet<>(clauses));
        final List<Image> images = imageRepository.findAllById(
                contractDTO.getImages() == null ? Collections.emptyList() : contractDTO.getImages());
        if (images.size() != (contractDTO.getImages() == null ? 0 : contractDTO.getImages().size())) {
            throw new NotFoundException("one of images not found");
        }
        contract.setImages(new HashSet<>(images));
        return contract;
    }

}
