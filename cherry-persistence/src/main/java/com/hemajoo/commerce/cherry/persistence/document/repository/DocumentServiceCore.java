/*
 * (C) Copyright Resse Christophe 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Resse Christophe. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Resse C. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Resse Christophe (christophe.resse@gmail.com).
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.persistence.document.repository;

import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.persistence.content.ProxyContentStore;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the document persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Service
public class DocumentServiceCore implements DocumentService
{
    /**
     * Document repository.
     */
    @Getter
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ProxyContentStore proxyStore;

    @Override
    public DocumentRepository getRepository()
    {
        return documentRepository;
    }

    @Override
    public Long count()
    {
        return documentRepository.count();
    }

    @Override
    public ServerDocumentEntity findById(UUID id)
    {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public ServerDocumentEntity save(ServerDocumentEntity document)
    {
        document = documentRepository.save(document);

        // and save the associated content file, if one!
        if (document.getContent() != null)
        {
//            documentStore.setContent(document, document.getContent());
            proxyStore.getStore().setContent(document, document.getContent());
        }

        return document;
    }

    @Override
    public ServerDocumentEntity saveAndFlush(ServerDocumentEntity document)
    {
        return documentRepository.saveAndFlush(document);
    }

    @Override
    public void deleteById(UUID id)
    {
        ServerDocumentEntity document = findById(id);

        // If a content file is associated, then delete it!
        if (document != null && document.getContentId() != null)
        {
            //documentStore.unsetContent(document);
            proxyStore.getStore().unsetContent(document);
        }

        documentRepository.deleteById(id);
    }

    @Override
    public List<ServerDocumentEntity> findAll()
    {
        // TODO We should for each document inject its content such as for the findById
        return documentRepository.findAll();
    }

    @Override
    public void loadContent(ServerDocumentEntity document)
    {
//        document.setContent(documentStore.getContent(document));
        document.setContent(proxyStore.getStore().getContent(document));
    }

    @Override
    public void loadContent(UUID documentId) throws DocumentException
    {
        ServerDocumentEntity document = findById(documentId);
        if (document != null)
        {
            loadContent(document);
        }

        throw new DocumentException(String.format("Cannot find document id.: '%s'", documentId.toString()));
    }
}

