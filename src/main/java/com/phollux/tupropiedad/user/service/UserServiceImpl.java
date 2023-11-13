package com.phollux.tupropiedad.user.service;

import com.phollux.tupropiedad.document_type.domain.DocumentType;
import com.phollux.tupropiedad.document_type.repos.DocumentTypeRepository;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.plan.domain.Plan;
import com.phollux.tupropiedad.plan.repos.PlanRepository;
import com.phollux.tupropiedad.role.domain.Role;
import com.phollux.tupropiedad.role.repos.RoleRepository;
import com.phollux.tupropiedad.user.domain.User;
import com.phollux.tupropiedad.user.model.UserDTO;
import com.phollux.tupropiedad.user.repos.UserRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final PlanRepository planRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository,
            final DocumentTypeRepository documentTypeRepository,
            final PlanRepository planRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.planRepository = planRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SimplePage<UserDTO> findAll(final String filter, final Pageable pageable) {
        Page<User> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = userRepository.findAllById(longFilter, pageable);
        } else {
            page = userRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    @Override
    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    @Override
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setDocumentNumber(user.getDocumentNumber());
        userDTO.setRole(user.getRole() == null ? null : user.getRole().getId());
        userDTO.setDocumentType(user.getDocumentType() == null ? null : user.getDocumentType().getId());
        userDTO.setPlan(user.getPlan() == null ? null : user.getPlan().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setDocumentNumber(userDTO.getDocumentNumber());
        final Role role = userDTO.getRole() == null ? null : roleRepository.findById(userDTO.getRole())
                .orElseThrow(() -> new NotFoundException("role not found"));
        user.setRole(role);
        final DocumentType documentType = userDTO.getDocumentType() == null ? null : documentTypeRepository.findById(userDTO.getDocumentType())
                .orElseThrow(() -> new NotFoundException("documentType not found"));
        user.setDocumentType(documentType);
        final Plan plan = userDTO.getPlan() == null ? null : planRepository.findById(userDTO.getPlan())
                .orElseThrow(() -> new NotFoundException("plan not found"));
        user.setPlan(plan);
        return user;
    }

}
