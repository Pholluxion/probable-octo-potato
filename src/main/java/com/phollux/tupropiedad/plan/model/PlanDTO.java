package com.phollux.tupropiedad.plan.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanDTO {

    private Long id;

    @Size(max = 100)
    private String name;

    private List<Long> products;

}
