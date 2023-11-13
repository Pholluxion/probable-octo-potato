package com.phollux.tupropiedad.document_type.config;

import com.phollux.tupropiedad.document_type.domain.DocumentType;
import com.phollux.tupropiedad.document_type.repos.DocumentTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class DocumentTypeLoader implements ApplicationRunner {

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeLoader(final DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {

        if (documentTypeRepository.count() != 0) {
            return;
        }

        log.info("initializing Document Types");

        final DocumentType documentTypeCC = new DocumentType();
        documentTypeCC.setName("Cédula");
        documentTypeCC.setAbbreviation("CC");
        documentTypeCC.setDescription("");

        final DocumentType documentTypeCCDIG = new DocumentType();
        documentTypeCCDIG.setName("Cédula Electrónica");
        documentTypeCCDIG.setAbbreviation("CC_DIG");
        documentTypeCCDIG.setDescription("");

        final DocumentType documentTypeCE = new DocumentType();
        documentTypeCE.setName("Cédula de extranjería");
        documentTypeCE.setAbbreviation("CE");
        documentTypeCE.setDescription("");

        final DocumentType documentTypePassport = new DocumentType();
        documentTypePassport.setName("Pasaporte");
        documentTypePassport.setAbbreviation("Pasaporte");
        documentTypePassport.setDescription("");

        documentTypeRepository.save(documentTypeCC);
        documentTypeRepository.save(documentTypeCCDIG);
        documentTypeRepository.save(documentTypeCE);
        documentTypeRepository.save(documentTypePassport);



    }

}