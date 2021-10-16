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

import com.hemajoo.commerce.cherry.model.person.entity.PostalAddress;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.mapper.DocumentMapper;
import com.hemajoo.commerce.cherry.persistence.person.entity.PostalAddressServerEntity;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * A mapper interface providing services to map between {@link PostalAddress} and {@link PostalAddressServerEntity} and vice-versa.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = DocumentMapper.class)
public interface PostalAddressMapper
{
    /**
     * Instance to this bean mapper.
     */
    PostalAddressMapper INSTANCE = Mappers.getMapper(PostalAddressMapper.class);

    /**
     * Maps from a persistent entity to a client entity.
     * @param entity Source entity.
     * @param context Context object.
     * @return Client entity.
     */
    PostalAddress mapPersistence(PostalAddressServerEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a list of persistent entities to a list of client entities.
     * @param list List of persistent entities.
     * @param context Context object.
     * @return List of client entities.
     */
    List<PostalAddress> mapPersistenceList(List<PostalAddressServerEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a client entity to a persistent entity.
     * @param entity Source entity.
     * @param context Context object.
     * @return Persistent entity.
     */
    PostalAddressServerEntity mapClient(PostalAddress entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a list of client entities to a list of persistent entities.
     * @param list List of client entities.
     * @param context Context object.
     * @return List of persistent entities.
     */
    List<PostalAddressServerEntity> mapClientList(List<PostalAddress> list, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a persistent entity.
     * @param entity Source entity to copy.
     * @param context Context object.
     * @return Copy.
     */
    PostalAddressServerEntity copy(PostalAddressServerEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a client entity.
     * @param entity Source entity to copy.
     * @param context Context object.
     * @return Copy.
     */
    PostalAddress copy(PostalAddress entity, @Context CycleAvoidingMappingContext context);
}
