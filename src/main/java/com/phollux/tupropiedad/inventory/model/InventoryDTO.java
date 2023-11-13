package com.phollux.tupropiedad.inventory.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InventoryDTO {

    private Integer id;

    @NotNull
    @Size(max = 100)
    private String furnitureType;

    @NotNull
    @Size(max = 255)
    private String furnitureName;

    @NotNull
    private Integer quantity;

    @NotNull
    @Size(max = 100)
    private String condition;

}
