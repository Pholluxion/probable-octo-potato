package com.phollux.tupropiedad.income.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class IncomeDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String description;

    @NotNull
    private OffsetDateTime date;

    @NotNull
    private Double amount;

    @NotNull
    private Integer contract;

}
