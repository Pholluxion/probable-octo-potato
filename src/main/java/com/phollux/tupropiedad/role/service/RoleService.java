package com.phollux.tupropiedad.role.service;

import com.phollux.tupropiedad.role.model.RoleDTO;
import java.util.List;


public interface RoleService {

    List<RoleDTO> findAll();

    RoleDTO get(Long id);

    Long create(RoleDTO roleDTO);

    void update(Long id, RoleDTO roleDTO);

    void delete(Long id);

    boolean nameExists(String name);

}
