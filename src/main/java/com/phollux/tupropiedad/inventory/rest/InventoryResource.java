package com.phollux.tupropiedad.inventory.rest;

import com.phollux.tupropiedad.inventory.model.InventoryDTO;
import com.phollux.tupropiedad.inventory.service.InventoryService;
import com.phollux.tupropiedad.authentication.model.SimplePage;
import com.phollux.tupropiedad.util.UserRoles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/inventories", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class InventoryResource {

    private final InventoryService inventoryService;

    public InventoryResource(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(
            parameters = {
                    @Parameter(
                            name = "page",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "size",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "sort",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = String.class)
                    )
            }
    )
    @PreAuthorize("hasAuthority('" + UserRoles.USER + "')")
    @GetMapping
    public ResponseEntity<SimplePage<InventoryDTO>> getAllInventories(
            @RequestParam(required = false, name = "filter") final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(inventoryService.findAll(filter, pageable));
    }

    @PreAuthorize("hasAuthority('" + UserRoles.USER + "')")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventory(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(inventoryService.get(id));
    }

    @PreAuthorize("hasAuthority('" + UserRoles.ADMIN + "')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createInventory(
            @RequestBody @Valid final InventoryDTO inventoryDTO) {
        final Integer createdId = inventoryService.create(inventoryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('" + UserRoles.ADMIN + "')")
    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateInventory(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final InventoryDTO inventoryDTO) {
        inventoryService.update(id, inventoryDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInventory(@PathVariable(name = "id") final Integer id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
