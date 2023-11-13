package com.phollux.tupropiedad.expense.service;

import com.phollux.tupropiedad.expense.domain.Expense;
import com.phollux.tupropiedad.expense.model.ExpenseDTO;
import com.phollux.tupropiedad.expense.repos.ExpenseRepository;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.provider.domain.Provider;
import com.phollux.tupropiedad.provider.repos.ProviderRepository;
import com.phollux.tupropiedad.service.repos.ServiceRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ProviderRepository providerRepository;
    private final ServiceRepository serviceRepository;

    public ExpenseServiceImpl(final ExpenseRepository expenseRepository,
            final ProviderRepository providerRepository,
            final ServiceRepository serviceRepository) {
        this.expenseRepository = expenseRepository;
        this.providerRepository = providerRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public SimplePage<ExpenseDTO> findAll(final String filter, final Pageable pageable) {
        Page<Expense> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = expenseRepository.findAllById(longFilter, pageable);
        } else {
            page = expenseRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(expense -> mapToDTO(expense, new ExpenseDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public ExpenseDTO get(final Long id) {
        return expenseRepository.findById(id)
                .map(expense -> mapToDTO(expense, new ExpenseDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ExpenseDTO expenseDTO) {
        final Expense expense = new Expense();
        mapToEntity(expenseDTO, expense);
        return expenseRepository.save(expense).getId();
    }

    @Override
    public void update(final Long id, final ExpenseDTO expenseDTO) {
        final Expense expense = expenseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(expenseDTO, expense);
        expenseRepository.save(expense);
    }

    @Override
    public void delete(final Long id) {
        expenseRepository.deleteById(id);
    }

    private ExpenseDTO mapToDTO(final Expense expense, final ExpenseDTO expenseDTO) {
        expenseDTO.setId(expense.getId());
        expenseDTO.setName(expense.getName());
        expenseDTO.setDate(expense.getDate());
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setProvider(expense.getProvider() == null ? null : expense.getProvider().getId());
        expenseDTO.setService(expense.getService() == null ? null : expense.getService().getId());
        return expenseDTO;
    }

    private Expense mapToEntity(final ExpenseDTO expenseDTO, final Expense expense) {
        expense.setName(expenseDTO.getName());
        expense.setDate(expenseDTO.getDate());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        final Provider provider = expenseDTO.getProvider() == null ? null : providerRepository.findById(expenseDTO.getProvider())
                .orElseThrow(() -> new NotFoundException("provider not found"));
        expense.setProvider(provider);
        final com.phollux.tupropiedad.service.domain.Service service = expenseDTO.getService() == null ? null : serviceRepository.findById(expenseDTO.getService())
                .orElseThrow(() -> new NotFoundException("service not found"));
        expense.setService(service);
        return expense;
    }

}
