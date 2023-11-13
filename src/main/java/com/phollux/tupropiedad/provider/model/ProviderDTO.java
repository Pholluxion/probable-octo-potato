package com.phollux.tupropiedad.provider.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProviderDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String address;

    @Size(max = 50)
    private String phone;

    @Size(max = 255)
    private String email;

    private String website;

}
