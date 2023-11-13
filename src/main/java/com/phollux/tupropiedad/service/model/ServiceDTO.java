package com.phollux.tupropiedad.service.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private Double price;

}
