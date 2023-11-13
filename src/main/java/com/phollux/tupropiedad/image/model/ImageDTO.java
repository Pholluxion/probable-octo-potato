package com.phollux.tupropiedad.image.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class ImageDTO {

    private UUID id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 110)
    private String type;

    private byte[] imageData;

    @NotNull
    private Long size;

    @NotNull
    private String url;

}
