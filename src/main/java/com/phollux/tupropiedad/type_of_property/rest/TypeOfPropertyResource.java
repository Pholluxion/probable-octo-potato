package com.phollux.tupropiedad.type_of_property.rest;

import com.phollux.tupropiedad.type_of_property.model.TypeOfPropertyDTO;
import com.phollux.tupropiedad.type_of_property.service.TypeOfPropertyService;
import com.phollux.tupropiedad.util.UserRoles;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/typeOfProperties", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('" + UserRoles.ADMIN + "')")
@SecurityRequirement(name = "bearer-jwt")
public class TypeOfPropertyResource {

    private final TypeOfPropertyService typeOfPropertyService;

    public TypeOfPropertyResource(final TypeOfPropertyService typeOfPropertyService) {
        this.typeOfPropertyService = typeOfPropertyService;
    }

    @GetMapping
    public ResponseEntity<List<TypeOfPropertyDTO>> getAllTypeOfProperties() {
        return ResponseEntity.ok(typeOfPropertyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOfPropertyDTO> getTypeOfProperty(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(typeOfPropertyService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTypeOfProperty(
            @RequestBody @Valid final TypeOfPropertyDTO typeOfPropertyDTO) {
        final Integer createdId = typeOfPropertyService.create(typeOfPropertyDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateTypeOfProperty(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final TypeOfPropertyDTO typeOfPropertyDTO) {
        typeOfPropertyService.update(id, typeOfPropertyDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTypeOfProperty(@PathVariable(name = "id") final Integer id) {
        typeOfPropertyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
