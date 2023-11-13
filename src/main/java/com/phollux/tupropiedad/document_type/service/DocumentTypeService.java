package com.phollux.tupropiedad.document_type.service;

import com.phollux.tupropiedad.document_type.model.DocumentTypeDTO;
import java.util.List;


public interface DocumentTypeService {

    List<DocumentTypeDTO> findAll();

    DocumentTypeDTO get(Long id);

    Long create(DocumentTypeDTO documentTypeDTO);

    void update(Long id, DocumentTypeDTO documentTypeDTO);

    void delete(Long id);

    boolean nameExists(String name);

    boolean abbreviationExists(String abbreviation);

}
