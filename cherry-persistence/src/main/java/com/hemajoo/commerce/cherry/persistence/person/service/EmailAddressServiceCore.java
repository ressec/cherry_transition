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
package com.hemajoo.commerce.cherry.persistence.person.service;

import com.hemajoo.commerce.cherry.commons.type.StatusType;
import com.hemajoo.commerce.cherry.model.base.search.criteria.SearchCriteria;
import com.hemajoo.commerce.cherry.model.base.search.criteria.SearchOperation;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.search.SearchEmailAddress;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractServerAuditEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractServerStatusEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.persistence.base.specification.GenericSpecification;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.repository.EmailAddressRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the email address persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Validated
@Service
@Log4j2
public class EmailAddressServiceCore implements EmailAddressService
{
    /**
     * Email address repository.
     */
    @Autowired
    private EmailAddressRepository emailAddressRepository;

    /**
     * Document (content store) service.
     */
    @Autowired
    private DocumentService documentService;

    @Override
    public Long count()
    {
        return emailAddressRepository.count();
    }

    @Override
    public ServerEmailAddressEntity findById(UUID id)
    {
        return emailAddressRepository.findById(id).orElse(null);
    }

    @Override
    public ServerEmailAddressEntity save(final @NonNull ServerEmailAddressEntity emailAddress) throws EmailAddressException, DocumentException
    {
        emailAddressRepository.save(emailAddress);

        // Save the documents attached to the email address.
        if (emailAddress.getDocuments() != null)
        {
            emailAddress.getDocuments().forEach(this::saveDocumentContent);
        }

        return emailAddress;
    }

    @Override
    public void deleteById(UUID id)
    {
        emailAddressRepository.deleteById(id);
    }

    @Override
    public List<ServerEmailAddressEntity> findAll()
    {
        return emailAddressRepository.findAll();
    }

    @Override
    public List<ServerEmailAddressEntity> findByAddressType(final AddressType type)
    {
        return emailAddressRepository.findByAddressType(type);
    }

    @Override
    public List<ServerEmailAddressEntity> findByStatus(final StatusType status)
    {
        return emailAddressRepository.findByStatusType(status);
    }

    @Override
    public List<ServerEmailAddressEntity> findByIsDefaultEmail(final Boolean isDefaultEmail)
    {
        return emailAddressRepository.findByIsDefaultEmail(isDefaultEmail);
    }

    @Override
    public List<ServerEmailAddressEntity> findByPersonId(final UUID personId)
    {
        return emailAddressRepository.findByPersonId(personId);
    }

    @Override
    public List<ServerEmailAddressEntity> search(final @NonNull SearchEmailAddress search)
    {
        GenericSpecification<ServerEmailAddressEntity> specification = new GenericSpecification<>();

        // Inherited fields
        if (search.getCreatedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_CREATED_BY,
                    search.getCreatedBy(),
                    SearchOperation.MATCH));
        }
        if (search.getModifiedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_MODIFIED_BY,
                    search.getModifiedBy(),
                    SearchOperation.MATCH));
        }

        if (search.getId() != null)
        {
            specification.add(new SearchCriteria(
                    ServerBaseEntity.FIELD_ID,
                    UUID.fromString(search.getId().toString()),
                    SearchOperation.EQUAL));
        }

        if (search.getIsDefaultEmail() != null)
        {
            specification.add(new SearchCriteria(
                    ServerEmailAddressEntity.FIELD_IS_DEFAULT,
                    search.getIsDefaultEmail(),
                    SearchOperation.EQUAL));
        }

        if (search.getAddressType() != null)
        {
            specification.add(new SearchCriteria(
                    ServerEmailAddressEntity.FIELD_ADDRESS_TYPE,
                    search.getAddressType(),
                    SearchOperation.EQUAL));
        }

        if (search.getStatusType() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerStatusEntity.FIELD_STATUS_TYPE,
                    search.getStatusType(),
                    SearchOperation.EQUAL));
        }

        if (search.getEmail() != null)
        {
            specification.add(new SearchCriteria(
                    ServerEmailAddressEntity.FIELD_EMAIL,
                    search.getEmail(),
                    SearchOperation.MATCH));
        }

        if (search.getPersonId() != null)
        {
            specification.add(new SearchCriteria(
                    ServerEmailAddressEntity.FIELD_PERSON,
                    search.getPersonId(),
                    SearchOperation.EQUAL_OBJECT_UUID));
        }

        if (specification.count() == 0)
        {
            specification.add(new SearchCriteria(
                    ServerEmailAddressEntity.FIELD_PERSON,
                    "00000000-0000-0000-0000-000000000000",
                    SearchOperation.EQUAL_OBJECT_UUID));
        }

        return emailAddressRepository.findAll(specification);
    }

    /**
     * Save the document content to the content store.
     * @param document Document.
     * @throws DocumentException Thrown if an error occurred while trying to save the document content to the content store.
     */
    private void saveDocumentContent(final @NonNull ServerDocumentEntity document) throws DocumentException
    {
        try
        {
            if (document.getContentId() == null) // Not stored in the content store.
            {
                documentService.save(document);
            }
        }
        catch (Exception e)
        {
            LOGGER.info(String.format("Cannot save document content id: %s for owner of type: %s, identifier: %s due to: %s",
                    document.getContentId(),
                    document.getOwner().getEntityType(),
                    document.getOwner().getId(),
                    e.getMessage()), e);
            throw new DocumentException(e.getMessage());
        }
    }
}
