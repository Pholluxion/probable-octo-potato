package com.phollux.tupropiedad.document_type.rest;

import com.phollux.tupropiedad.document_type.model.DocumentTypeDTO;
import com.phollux.tupropiedad.document_type.service.DocumentTypeService;
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
@RequestMapping(value = "/api/documentTypes", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('" + UserRoles.ADMIN + "')")
@SecurityRequirement(name = "bearer-jwt")
public class DocumentTypeResource {

    private final DocumentTypeService documentTypeService;

    public DocumentTypeResource(final DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @GetMapping
    public ResponseEntity<List<DocumentTypeDTO>> getAllDocumentTypes() {
        return ResponseEntity.ok(documentTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeDTO> getDocumentType(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(documentTypeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDocumentType(
            @RequestBody @Valid final DocumentTypeDTO documentTypeDTO) {
        final Long createdId = documentTypeService.create(documentTypeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDocumentType(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DocumentTypeDTO documentTypeDTO) {
        documentTypeService.update(id, documentTypeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable(name = "id") final Long id) {
        documentTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
