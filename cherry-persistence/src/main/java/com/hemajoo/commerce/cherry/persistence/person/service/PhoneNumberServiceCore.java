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
import com.hemajoo.commerce.cherry.model.person.search.SearchPhoneNumber;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractAuditServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractStatusServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.specification.GenericSpecification;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.persistence.person.repository.PhoneNumberRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the phone number persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Service
public class PhoneNumberServiceCore implements PhoneNumberService
{
    /**
     * Repository for the phone numbers.
     */
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;


    @Override
    public Long count()
    {
        return phoneNumberRepository.count();
    }

    @Override
    public ServerPhoneNumberEntity findById(UUID id)
    {
        return phoneNumberRepository.findById(id).orElse(null);
    }

    @Override
    public ServerPhoneNumberEntity save(ServerPhoneNumberEntity phoneNumber)
    {
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public void deleteById(UUID id)
    {
        phoneNumberRepository.deleteById(id);
    }

    @Override
    public List<ServerPhoneNumberEntity> findAll()
    {
        return phoneNumberRepository.findAll();
    }

    @Override
    public List<ServerPhoneNumberEntity> findByPhoneType(PhoneNumberType type)
    {
        return phoneNumberRepository.findByPhoneType(type);
    }

    @Override
    public List<ServerPhoneNumberEntity> findByCategoryType(PhoneNumberCategoryType category)
    {
        return phoneNumberRepository.findByCategoryType(category);
    }

    @Override
    public List<ServerPhoneNumberEntity> findByStatus(StatusType status)
    {
        return phoneNumberRepository.findByStatusType(status);
    }

    @Override
    public List<ServerPhoneNumberEntity> findByIsDefault(boolean isDefault)
    {
        return phoneNumberRepository.findByIsDefault(isDefault);
    }

    @Override
    public List<ServerPhoneNumberEntity> findByPersonId(long personId)
    {
        return phoneNumberRepository.findByPersonId(personId);
    }

    @Override
    public List<ServerPhoneNumberEntity> search(@NonNull SearchPhoneNumber phoneNumber)
    {
        GenericSpecification<ServerPhoneNumberEntity> specification = new GenericSpecification<>();

        // Inherited fields
        if (phoneNumber.getCreatedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractAuditServerEntity.FIELD_CREATED_BY,
                    phoneNumber.getCreatedBy(),
                    SearchOperation.MATCH));
        }

        if (phoneNumber.getModifiedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractAuditServerEntity.FIELD_MODIFIED_BY,
                    phoneNumber.getModifiedBy(),
                    SearchOperation.MATCH));
        }

        if (phoneNumber.getStatusType() != null && phoneNumber.getStatusType() != StatusType.UNSPECIFIED)
        {
            specification.add(new SearchCriteria(
                    AbstractStatusServerEntity.FIELD_STATUS_TYPE,
                    phoneNumber.getStatusType(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getId() != null)
        {
            specification.add(new SearchCriteria(
                    ServerPhoneNumberEntity.FIELD_ID,
                    phoneNumber.getId(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getIsDefault() != null)
        {
            specification.add(new SearchCriteria(
                    ServerPhoneNumberEntity.FIELD_IS_DEFAULT,
                    phoneNumber.getIsDefault(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getPhoneType() != null && phoneNumber.getPhoneType() != PhoneNumberType.UNSPECIFIED)
        {
            specification.add(new SearchCriteria(
                    ServerPhoneNumberEntity.FIELD_PHONE_TYPE,
                    phoneNumber.getPhoneType(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getPersonId() != null)
        {
            specification.add(new SearchCriteria(
                    ServerPhoneNumberEntity.FIELD_PERSON_ID,
                    phoneNumber.getPersonId(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getCategoryType() != null && phoneNumber.getCategoryType() != PhoneNumberCategoryType.UNSPECIFIED)
        {
            specification.add(new SearchCriteria(
                    ServerPhoneNumberEntity.FIELD_PHONE_CATEGORY_TYPE,
                    phoneNumber.getCategoryType(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getCountryCode() != null)
        {
            specification.add(new SearchCriteria(
                    ServerPhoneNumberEntity.FIELD_COUNTRY_CODE,
                    phoneNumber.getCountryCode(),
                    SearchOperation.MATCH));
        }

        if (phoneNumber.getNumber() != null)
        {
            specification.add(new SearchCriteria(
                    ServerPhoneNumberEntity.FIELD_NUMBER,
                    phoneNumber.getNumber(),
                    SearchOperation.MATCH));
        }

        return phoneNumberRepository.findAll(specification);
    }
}
