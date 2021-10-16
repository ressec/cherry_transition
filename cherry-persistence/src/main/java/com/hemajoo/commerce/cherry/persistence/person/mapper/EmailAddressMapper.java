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
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.mapper.DocumentMapper;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
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
public interface EmailAddressMapper
{
    /**
     * Instance to the bean mapper.
     */
    EmailAddressMapper INSTANCE = Mappers.getMapper(EmailAddressMapper.class);

    /**
     * Maps a client entity to a persistent entity.
     * @param entity Client entity to map.
     * @param context Context object.
     * @return Mapped persistent entity.
     */
    EmailAddressServerEntity fromClient(EmailAddress entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps a list of client entities to a list of persistent entities.
     * @param list List of client entities to maps.
     * @param context Context object.
     * @return Mapped list of persistent entities.
     */
    List<EmailAddressServerEntity> fromClientList(List<EmailAddress> list, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a persistent entity to a client entity.
     * @param entity Persistent entity to map.
     * @param context Context object.
     * @return Mapped client entity.
     */
    EmailAddress fromServer(EmailAddressServerEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a list of persistent entities to a list of client entities.
     * @param list List of persistent entities to map.
     * @param context Context object.
     * @return Mapped list of client entities.
     */
    List<EmailAddress> fromServerList(List<EmailAddressServerEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a persistent entity.
     * @param entity Source persistent entity to copy.
     * @param context Context object.
     * @return Copied persistent entity.
     */
    EmailAddressServerEntity copy(EmailAddressServerEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a client entity.
     * @param entity Source client entity to copy.
     * @param context Context object.
     * @return Copied client entity.
     */
    EmailAddress copy(EmailAddress entity, @Context CycleAvoidingMappingContext context);
}
