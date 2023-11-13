package com.phollux.tupropiedad.clause.rest;

import com.phollux.tupropiedad.clause.model.ClauseDTO;
import com.phollux.tupropiedad.clause.service.ClauseService;
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
@RequestMapping(value = "/api/clauses", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('" + UserRoles.ADMIN + "')")
@SecurityRequirement(name = "bearer-jwt")
public class ClauseResource {

    private final ClauseService clauseService;

    public ClauseResource(final ClauseService clauseService) {
        this.clauseService = clauseService;
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
    @GetMapping
    public ResponseEntity<SimplePage<ClauseDTO>> getAllClauses(
            @RequestParam(required = false, name = "filter") final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(clauseService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClauseDTO> getClause(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(clauseService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createClause(@RequestBody @Valid final ClauseDTO clauseDTO) {
        final Long createdId = clauseService.create(clauseDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateClause(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ClauseDTO clauseDTO) {
        clauseService.update(id, clauseDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClause(@PathVariable(name = "id") final Long id) {
        clauseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
