package com.phollux.tupropiedad.authentication.service;

import com.phollux.tupropiedad.document_type.repos.DocumentTypeRepository;
import com.phollux.tupropiedad.authentication.model.RegistrationRequest;
import com.phollux.tupropiedad.role.repos.RoleRepository;
import com.phollux.tupropiedad.user.domain.User;
import com.phollux.tupropiedad.user.repos.UserRepository;
import com.phollux.tupropiedad.util.UserRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final DocumentTypeRepository documentTypeRepository;

    public RegistrationService(final UserRepository userRepository,
                               final PasswordEncoder passwordEncoder, final RoleRepository roleRepository,
                               final DocumentTypeRepository documentTypeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.documentTypeRepository = documentTypeRepository;
    }

    public boolean emailExists(final RegistrationRequest registrationRequest) {
        return userRepository.existsByEmailIgnoreCase(registrationRequest.getEmail());
    }

    public void register(final RegistrationRequest registrationRequest) {
        log.info("registering new user: {}", registrationRequest.getEmail());

        final User user = new User();
        user.setName(registrationRequest.getName());
        user.setSurname(registrationRequest.getSurname());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setDocumentNumber(registrationRequest.getDocumentNumber());

        // assign default role
        if(registrationRequest.getName().matches(".*(TPAdmin).*")){
            user.setRole(roleRepository.findByName(UserRoles.ADMIN));
        }else{
            user.setRole(roleRepository.findByName(UserRoles.USER));
        }
        // assign default Document Type
        user.setDocumentType(documentTypeRepository.findByName(com.phollux.tuhome.util.DocumentTypes.CC));
        userRepository.save(user);
    }

}
