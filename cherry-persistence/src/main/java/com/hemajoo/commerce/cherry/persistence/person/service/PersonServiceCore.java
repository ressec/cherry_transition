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
import com.hemajoo.commerce.cherry.model.person.search.PersonSearch;
import com.hemajoo.commerce.cherry.model.person.type.GenderType;
import com.hemajoo.commerce.cherry.model.person.type.PersonType;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractAuditServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractStatusServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.BaseServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.specification.GenericSpecification;
import com.hemajoo.commerce.cherry.persistence.document.entity.DocumentServerEntity;
import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.PersonServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.repository.EmailAddressRepository;
import com.hemajoo.commerce.cherry.persistence.person.repository.PersonRepository;
import com.hemajoo.commerce.cherry.persistence.person.repository.PhoneNumberRepository;
import com.hemajoo.commerce.cherry.persistence.person.repository.PostalAddressRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the person persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Service
public class PersonServiceCore implements PersonService
{
    /**
     * Repository for the persons.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Repository for the email addresses.
     */
    @Autowired
    private EmailAddressRepository emailAddressRepository;

    /**
     * Repository for the postal addresses.
     */
    @Autowired
    private PostalAddressRepository postalAddressRepository;

    /**
     * Repository for the phone numbers.
     */
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    /**
     * Document service.
     */
    @Autowired
    private DocumentService documentService;

    /**
     * Email address service.
     */
    @Autowired
    private EmailAddressService emailAddressService;

    @Override
    public PersonRepository getRepository()
    {
        return personRepository;
    }

    @Override
    public Long count()
    {
        return personRepository.count();
    }

    @Override
    public PersonServerEntity findById(UUID id)
    {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackOn = DocumentException.class)
    public PersonServerEntity save(PersonServerEntity person) throws DocumentException
    {
        if (person.getId() == null)
        {
            person = personRepository.save(person);
        }

        // REMINDER Important to save the underlying collections of entities that hold documents!

        // Save the documents directly attached to the person.
        for (DocumentServerEntity document : person.getDocuments())
        {
            try
            {
                documentService.save(document);
            }
            catch (Exception e)
            {
                throw new DocumentException(e.getMessage());
            }
        }

        // Save the email addresses directly attached to the person.
        for (EmailAddressServerEntity email : person.getEmailAddresses())
        {
            try
            {
                emailAddressService.save(email);
            }
            catch (Exception e)
            {
                throw new DocumentException(e.getMessage());
            }
        }

        return person;
    }

    @Override
    public List<DocumentServerEntity> getDocuments(@NonNull BaseServerEntity entity)
    {
        return entity.getDocuments();
    }

    @Override
    public void saveAndFlush(@NonNull PersonServerEntity person)
    {
        personRepository.saveAndFlush(person);
    }

    @Override
    public void deleteById(UUID id)
    {
        personRepository.deleteById(id);
    }

    @Override
    public List<PersonServerEntity> findAll()
    {
        return personRepository.findAll();
    }

    @Override
    public List<PersonServerEntity> search(@NonNull PersonSearch person)
    {
        GenericSpecification<PersonServerEntity> specification = new GenericSpecification<>();

        // Inherited fields
        if (person.getCreatedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractAuditServerEntity.FIELD_CREATED_BY,
                    person.getCreatedBy(),
                    SearchOperation.MATCH));
        }

        if (person.getModifiedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractAuditServerEntity.FIELD_MODIFIED_BY,
                    person.getModifiedBy(),
                    SearchOperation.MATCH));
        }

        if (person.getId() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServerEntity.FIELD_ID,
                    person.getId(),
                    SearchOperation.EQUAL));
        }

        if (person.getPersonType() != null && person.getPersonType() != PersonType.UNSPECIFIED)
        {
            specification.add(new SearchCriteria(
                    PersonServerEntity.FIELD_PERSON_TYPE,
                    person.getPersonType(),
                    SearchOperation.EQUAL));
        }

        if (person.getStatusType() != null && person.getStatusType() != StatusType.UNSPECIFIED)
        {
            specification.add(new SearchCriteria(
                    AbstractStatusServerEntity.FIELD_STATUS_TYPE,
                    person.getStatusType(),
                    SearchOperation.EQUAL));
        }

        if (person.getGenderType() != null && person.getGenderType() != GenderType.UNSPECIFIED)
        {
            specification.add(new SearchCriteria(
                    PersonServerEntity.FIELD_GENDER_TYPE,
                    person.getGenderType(),
                    SearchOperation.EQUAL));
        }

        if (person.getBirthDate() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServerEntity.FIELD_BIRTHDATE,
                    person.getBirthDate(),
                    SearchOperation.EQUAL));
        }

        if (person.getLastName() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServerEntity.FIELD_LASTNAME,
                    person.getLastName(),
                    SearchOperation.MATCH));
        }

        if (person.getFirstName() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServerEntity.FIELD_FIRSTNAME,
                    person.getFirstName(),
                    SearchOperation.MATCH));
        }

        return personRepository.findAll(specification);
    }

    @Override
    public List<EmailAddressServerEntity> getEmailAddresses(final @NonNull PersonServerEntity person)
    {
        return emailAddressRepository.findByPersonId(person.getId());
    }

    @Override
    public PersonServerEntity loadEmailAddresses(final @NonNull PersonServerEntity person)
    {
        person.setEmailAddresses(getEmailAddresses(person));

        return person;
    }

//    private void saveDocument(final @NonNull DocumentServerEntity document) throws DocumentException
//    {
//        try
//        {
//            if (document.getContentId() == null) // Not stored in the content store.
//            {
//                documentService.save(document);
//            }
//        }
//        catch (Exception e)
//        {
//            throw new DocumentException(e.getMessage());
//        }
//    }
}

