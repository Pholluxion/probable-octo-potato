package com.phollux.tupropiedad.clause.service;

import com.phollux.tupropiedad.clause.domain.Clause;
import com.phollux.tupropiedad.clause.model.ClauseDTO;
import com.phollux.tupropiedad.clause.repos.ClauseRepository;
import com.phollux.tupropiedad.contract.repos.ContractRepository;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.util.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ClauseServiceImpl implements ClauseService {

    private final ClauseRepository clauseRepository;
    private final ContractRepository contractRepository;

    public ClauseServiceImpl(final ClauseRepository clauseRepository,
            final ContractRepository contractRepository) {
        this.clauseRepository = clauseRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public SimplePage<ClauseDTO> findAll(final String filter, final Pageable pageable) {
        Page<Clause> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = clauseRepository.findAllById(longFilter, pageable);
        } else {
            page = clauseRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(clause -> mapToDTO(clause, new ClauseDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public ClauseDTO get(final Long id) {
        return clauseRepository.findById(id)
                .map(clause -> mapToDTO(clause, new ClauseDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ClauseDTO clauseDTO) {
        final Clause clause = new Clause();
        mapToEntity(clauseDTO, clause);
        return clauseRepository.save(clause).getId();
    }

    @Override
    public void update(final Long id, final ClauseDTO clauseDTO) {
        final Clause clause = clauseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clauseDTO, clause);
        clauseRepository.save(clause);
    }

    @Override
    public void delete(final Long id) {
        final Clause clause = clauseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        contractRepository.findAllByClauses(clause)
                .forEach(contract -> contract.getClauses().remove(clause));
        clauseRepository.delete(clause);
    }

    private ClauseDTO mapToDTO(final Clause clause, final ClauseDTO clauseDTO) {
        clauseDTO.setId(clause.getId());
        clauseDTO.setName(clause.getName());
        clauseDTO.setDescription(clause.getDescription());
        return clauseDTO;
    }

    private Clause mapToEntity(final ClauseDTO clauseDTO, final Clause clause) {
        clause.setName(clauseDTO.getName());
        clause.setDescription(clauseDTO.getDescription());
        return clause;
    }

    @Override
    public boolean nameExists(final String name) {
        return clauseRepository.existsByNameIgnoreCase(name);
    }

}
