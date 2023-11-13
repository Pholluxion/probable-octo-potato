package com.phollux.tupropiedad.image.repos;


import com.phollux.tupropiedad.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
