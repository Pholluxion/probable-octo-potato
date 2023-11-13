package com.phollux.tupropiedad.income.service;

import com.phollux.tupropiedad.contract.domain.Contract;
import com.phollux.tupropiedad.contract.repos.ContractRepository;
import com.phollux.tupropiedad.income.domain.Income;
import com.phollux.tupropiedad.income.model.IncomeDTO;
import com.phollux.tupropiedad.income.repos.IncomeRepository;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final ContractRepository contractRepository;

    public IncomeServiceImpl(final IncomeRepository incomeRepository,
            final ContractRepository contractRepository) {
        this.incomeRepository = incomeRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public SimplePage<IncomeDTO> findAll(final String filter, final Pageable pageable) {
        Page<Income> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = incomeRepository.findAllById(longFilter, pageable);
        } else {
            page = incomeRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(income -> mapToDTO(income, new IncomeDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public IncomeDTO get(final Long id) {
        return incomeRepository.findById(id)
                .map(income -> mapToDTO(income, new IncomeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final IncomeDTO incomeDTO) {
        final Income income = new Income();
        mapToEntity(incomeDTO, income);
        return incomeRepository.save(income).getId();
    }

    @Override
    public void update(final Long id, final IncomeDTO incomeDTO) {
        final Income income = incomeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(incomeDTO, income);
        incomeRepository.save(income);
    }

    @Override
    public void delete(final Long id) {
        incomeRepository.deleteById(id);
    }

    private IncomeDTO mapToDTO(final Income income, final IncomeDTO incomeDTO) {
        incomeDTO.setId(income.getId());
        incomeDTO.setName(income.getName());
        incomeDTO.setDescription(income.getDescription());
        incomeDTO.setDate(income.getDate());
        incomeDTO.setAmount(income.getAmount());
        incomeDTO.setContract(income.getContract() == null ? null : income.getContract().getId());
        return incomeDTO;
    }

    private Income mapToEntity(final IncomeDTO incomeDTO, final Income income) {
        income.setName(incomeDTO.getName());
        income.setDescription(incomeDTO.getDescription());
        income.setDate(incomeDTO.getDate());
        income.setAmount(incomeDTO.getAmount());
        final Contract contract = incomeDTO.getContract() == null ? null : contractRepository.findById(incomeDTO.getContract())
                .orElseThrow(() -> new NotFoundException("contract not found"));
        income.setContract(contract);
        return income;
    }

}
