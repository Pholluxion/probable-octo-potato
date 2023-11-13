package com.phollux.tupropiedad.role.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

}
