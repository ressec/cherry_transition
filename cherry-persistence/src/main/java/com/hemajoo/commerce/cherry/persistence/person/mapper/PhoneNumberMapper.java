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
package com.hemajoo.commerce.cherry.persistence.person.mapper;

import com.hemajoo.commerce.cherry.model.person.entity.EmailAddress;
import com.hemajoo.commerce.cherry.model.person.entity.PhoneNumber;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.mapper.DocumentMapper;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.PhoneNumberServerEntity;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * A mapper interface providing services to map between {@link EmailAddress} and {@link EmailAddressServerEntity} and vice-versa.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = DocumentMapper.class)
public interface PhoneNumberMapper
{
    /**
     * Instance to this bean mapper.
     */
    PhoneNumberMapper INSTANCE = Mappers.getMapper(PhoneNumberMapper.class);

    /**
     * Maps from a persistent entity to a client entity.
     * @param entity Source entity to map.
     * @param context Context object.
     * @return Client entity.
     */
    PhoneNumber fromServer(PhoneNumberServerEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a list of persistent entities to a list of client entities.
     * @param list List of persistent entities to map.
     * @param context Context object.
     * @return List of client entities.
     */
    List<PhoneNumber> fromServerList(List<PhoneNumberServerEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a client entity to a persistent entity.
     * @param entity Source entity to map.
     * @param context Context object.
     * @return Persistent entity.
     */
    PhoneNumberServerEntity fromClient(PhoneNumber entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a list of client entities to a list of persistent entities.
     * @param list List of client entities.
     * @param context Context object.
     * @return List of persistent entities.
     */
    List<PhoneNumberServerEntity> fromClientList(List<PhoneNumber> list, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a persistent entity.
     * @param entity Source entity to copy.
     * @param context Context object.
     * @return Copy.
     */
    PhoneNumberServerEntity copy(PhoneNumberServerEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a client entity.
     * @param entity Source entity to copy.
     * @param context Context object.
     * @return Copy.
     */
    PhoneNumber copy(PhoneNumber entity, @Context CycleAvoidingMappingContext context);
}
