package com.phollux.tupropiedad.document_type.service;

import com.phollux.tupropiedad.document_type.domain.DocumentType;
import com.phollux.tupropiedad.document_type.model.DocumentTypeDTO;
import com.phollux.tupropiedad.document_type.repos.DocumentTypeRepository;
import com.phollux.tupropiedad.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeServiceImpl(final DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public List<DocumentTypeDTO> findAll() {
        final List<DocumentType> documentTypes = documentTypeRepository.findAll(Sort.by("id"));
        return documentTypes.stream()
                .map(documentType -> mapToDTO(documentType, new DocumentTypeDTO()))
                .toList();
    }

    @Override
    public DocumentTypeDTO get(final Long id) {
        return documentTypeRepository.findById(id)
                .map(documentType -> mapToDTO(documentType, new DocumentTypeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final DocumentTypeDTO documentTypeDTO) {
        final DocumentType documentType = new DocumentType();
        mapToEntity(documentTypeDTO, documentType);
        return documentTypeRepository.save(documentType).getId();
    }

    @Override
    public void update(final Long id, final DocumentTypeDTO documentTypeDTO) {
        final DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(documentTypeDTO, documentType);
        documentTypeRepository.save(documentType);
    }

    @Override
    public void delete(final Long id) {
        documentTypeRepository.deleteById(id);
    }

    private DocumentTypeDTO mapToDTO(final DocumentType documentType,
            final DocumentTypeDTO documentTypeDTO) {
        documentTypeDTO.setId(documentType.getId());
        documentTypeDTO.setName(documentType.getName());
        documentTypeDTO.setAbbreviation(documentType.getAbbreviation());
        documentTypeDTO.setDescription(documentType.getDescription());
        return documentTypeDTO;
    }

    private DocumentType mapToEntity(final DocumentTypeDTO documentTypeDTO,
            final DocumentType documentType) {
        documentType.setName(documentTypeDTO.getName());
        documentType.setAbbreviation(documentTypeDTO.getAbbreviation());
        documentType.setDescription(documentTypeDTO.getDescription());
        return documentType;
    }

    @Override
    public boolean nameExists(final String name) {
        return documentTypeRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean abbreviationExists(final String abbreviation) {
        return documentTypeRepository.existsByAbbreviationIgnoreCase(abbreviation);
    }

}
