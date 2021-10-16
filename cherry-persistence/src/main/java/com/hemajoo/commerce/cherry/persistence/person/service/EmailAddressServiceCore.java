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
import com.hemajoo.commerce.cherry.model.document.DocumentException;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.search.EmailAddressSearch;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractAuditServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractStatusServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.BaseServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.specification.GenericSpecification;
import com.hemajoo.commerce.cherry.persistence.document.entity.DocumentServerEntity;
import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.repository.EmailAddressRepository;
import lombok.NonNull;
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
public class EmailAddressServiceCore implements EmailAddressService
{
    /**
     * Service for the persons.
     */
    @Autowired
    private PersonService personService;

    /**
     * Repository of the email addresses.
     */
    @Autowired
    private EmailAddressRepository emailAddressRepository;

    /**
     * Document service.
     */
    @Autowired
    private DocumentService documentService;

    @Override
    public Long count()
    {
        return emailAddressRepository.count();
    }

    @Override
    public EmailAddressServerEntity findById(UUID id)
    {
        return emailAddressRepository.findById(id).orElse(null);
    }

    @Override
    public EmailAddressServerEntity save(final @NonNull EmailAddressServerEntity emailAddress) throws EmailAddressException, DocumentException
    {
        emailAddressRepository.save(emailAddress);

        // Save the document attached to the email address.
        for (DocumentServerEntity document : emailAddress.getDocuments())
        {
            try
            {
                saveDocumentContent(document);
            }
            catch (Exception e)
            {
                throw new DocumentException(e.getMessage());
            }
        }

        return emailAddress;
    }

    @Override
    public void deleteById(UUID id)
    {
        emailAddressRepository.deleteById(id);
    }

    @Override
    public List<EmailAddressServerEntity> findAll()
    {
        return emailAddressRepository.findAll();
    }

    @Override
    public List<EmailAddressServerEntity> findByAddressType(final AddressType type)
    {
        return emailAddressRepository.findByAddressType(type);
    }

    @Override
    public List<EmailAddressServerEntity> findByStatus(final StatusType status)
    {
        return emailAddressRepository.findByStatusType(status);
    }

    @Override
    public List<EmailAddressServerEntity> findByDefaultEmail(final boolean defaultEmail)
    {
        return emailAddressRepository.findByDefaultEmail(defaultEmail);
    }

    @Override
    public List<EmailAddressServerEntity> findByPersonId(final UUID personId)
    {
        return emailAddressRepository.findByPersonId(personId);
    }

    @Override
    public List<EmailAddressServerEntity> search(final @NonNull EmailAddressSearch search)
    {
        GenericSpecification<EmailAddressServerEntity> specification = new GenericSpecification<>();

        // Inherited fields
        if (search.getCreatedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractAuditServerEntity.FIELD_CREATED_BY,
                    search.getCreatedBy(),
                    SearchOperation.MATCH));
        }
        if (search.getModifiedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractAuditServerEntity.FIELD_MODIFIED_BY,
                    search.getModifiedBy(),
                    SearchOperation.MATCH));
        }

        if (search.getId() != null)
        {
            specification.add(new SearchCriteria(
                    BaseServerEntity.FIELD_ID,
                    UUID.fromString(search.getId().toString()),
                    SearchOperation.EQUAL));
        }

        specification.add(new SearchCriteria(
                EmailAddressServerEntity.FIELD_IS_DEFAULT,
                search.isDefaultEmail(),
                SearchOperation.EQUAL));

        if (search.getAddressType() != null && search.getAddressType() != AddressType.UNSPECIFIED)
        {
            specification.add(new SearchCriteria(
                    EmailAddressServerEntity.FIELD_ADDRESS_TYPE,
                    search.getAddressType(),
                    SearchOperation.EQUAL));
        }

        if (search.getStatusType() != null && search.getStatusType() != StatusType.UNSPECIFIED)
        {
            specification.add(new SearchCriteria(
                    AbstractStatusServerEntity.FIELD_STATUS_TYPE,
                    search.getStatusType(),
                    SearchOperation.EQUAL));
        }

        if (search.getEmail() != null)
        {
            specification.add(new SearchCriteria(
                    EmailAddressServerEntity.FIELD_EMAIL,
                    search.getEmail(),
                    SearchOperation.MATCH));
        }

        if (search.getPersonId() != null)
        {
            specification.add(new SearchCriteria(
                    EmailAddressServerEntity.FIELD_PERSON,
                    search.getPersonId(),
                    SearchOperation.EQUAL_OBJECT_UUID));
        }

        return emailAddressRepository.findAll(specification);
    }

    /**
     * Save the document content to the content store.
     * @param document Document.
     * @throws DocumentException Thrown if an error occurred while trying to save the document content to the content store.
     */
    private void saveDocumentContent(final @NonNull DocumentServerEntity document) throws DocumentException
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
            throw new DocumentException(e.getMessage());
        }
    }
}