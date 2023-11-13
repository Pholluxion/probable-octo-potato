package com.phollux.tupropiedad.expense.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExpenseDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private OffsetDateTime date;

    @NotNull
    private String description;

    @NotNull
    private Double amount;

    private Long provider;

    private Long service;

}
