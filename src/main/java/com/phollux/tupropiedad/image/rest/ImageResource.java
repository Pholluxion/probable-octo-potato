package com.phollux.tupropiedad.image.rest;

import com.phollux.tupropiedad.image.model.ImageDTO;
import com.phollux.tupropiedad.image.service.ImageService;
import com.phollux.tupropiedad.util.UserRoles;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping(value = "/api/images", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class ImageResource {

    private final ImageService imageService;

    public ImageResource(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public List<ImageDTO> getAllImages() {
        return imageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID id) {
        ImageDTO image = imageService.get(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "adjunto; Nombre del archivo=\"" + image.getName() + "\"")
                .contentType(MediaType.valueOf(image.getType()))
                .body(image.getImageData());
    }

    @PreAuthorize("hasAuthority('" + UserRoles.ADMIN + "')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createImage(@RequestParam("file") MultipartFile file) throws IOException {
        try {
           final ImageDTO image = imageService.create(file);

            String downloadURL =    ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/images/")
                    .path(String.valueOf(image.getId()))
                    .toUriString();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("url",downloadURL)
                    .body(String.format("El archivo ha subido correctamente: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("No se pudo cargar el archivo: %s!", file.getOriginalFilename()));
        }
    }

    @PreAuthorize("hasAuthority('" + UserRoles.ADMIN + "')")
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteImage(@PathVariable(name = "id") final UUID id) {
        imageService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
