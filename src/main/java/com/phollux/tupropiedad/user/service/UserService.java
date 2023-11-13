package com.phollux.tupropiedad.user.service;

import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.user.model.UserDTO;
import org.springframework.data.domain.Pageable;


public interface UserService {

    SimplePage<UserDTO> findAll(String filter, Pageable pageable);

    UserDTO get(Long id);

    Long create(UserDTO userDTO);

    void update(Long id, UserDTO userDTO);

    void delete(Long id);

}
