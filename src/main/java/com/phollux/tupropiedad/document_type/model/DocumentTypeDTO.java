package com.phollux.tupropiedad.document_type.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DocumentTypeDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 10)
    private String abbreviation;

    @Size(max = 255)
    private String description;

}
