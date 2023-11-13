package com.phollux.tupropiedad.image.service;

import com.phollux.tupropiedad.image.domain.Image;
import com.phollux.tupropiedad.image.model.ImageDTO;
import com.phollux.tupropiedad.image.repos.ImageRepository;
import com.phollux.tupropiedad.util.ImageUtil;
import com.phollux.tupropiedad.util.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class ImageServiceImpl implements  ImageService{

    private final ImageRepository imageRepository;

    public ImageServiceImpl(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<ImageDTO> findAll() {

        return imageRepository.findAll()
                .stream()
                .map(this::mapToImageResponse)
                .collect(Collectors.toList());
    }
    @Override
    public ImageDTO get(final UUID id) {

        return  imageRepository.findById(id)
                .map(image -> mapToDTO(image, new ImageDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(final UUID id) {
        imageRepository.deleteById(id);
    }


    @Override
    public ImageDTO create(MultipartFile file) throws IOException {

        byte[] imageData = ImageUtil.compressImage(file.getBytes());

        Image image = new Image();
        image.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        image.setType(file.getContentType());
        image.setImageData(imageData);
        image.setSize(file.getSize());

       return mapToDTO(imageRepository.save(image),new ImageDTO());
    }

    private ImageDTO mapToDTO(final Image image, final ImageDTO imageDTO) {

        byte[] imageData = ImageUtil.decompressImage(image.getImageData());

        imageDTO.setId(image.getId());
        imageDTO.setImageData(imageData);
        imageDTO.setName(image.getName());
        imageDTO.setType(image.getType());
        return imageDTO;
    }

    private ImageDTO mapToImageResponse(Image image) {

        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/images/")
                .path(String.valueOf(image.getId()))
                .toUriString();

        ImageDTO imageDTO = new ImageDTO();

        imageDTO.setId(image.getId());
        imageDTO.setName(image.getName());
        imageDTO.setType(image.getType());
        imageDTO.setSize(image.getSize());
        imageDTO.setUrl(downloadURL);

        return imageDTO;
    }

}
