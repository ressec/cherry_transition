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
package com.hemajoo.commerce.cherry.persistence.document.mapper;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.model.document.Document;
import com.hemajoo.commerce.cherry.model.document.DocumentException;
import com.hemajoo.commerce.cherry.persistence.base.entity.BaseServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.factory.ServerEntityFactory;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.entity.DocumentServerEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * A mapper interface providing services to map between {@link Document} and {@link DocumentServerEntity} and vice-versa.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DocumentMapper
{
    /**
     * Instance to this bean mapper.
     */
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    /**
     * Maps from a persistent entity to a client entity.
     * @param entity Persistent entity to convert.
     * @param context Context object.
     * @return Converted client entity.
     */
    @Mapping(source = "entity.owner", target = "owner", qualifiedByName = "toIdentity")
    Document fromServer(DocumentServerEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps a list of persistent entities to a list of client entities.
     * @param list List of persistent entities.
     * @param context Context object.
     * @return Converted list of client entities.
     */
    //@Mapping(target = "entity.owner", qualifiedByName = "toIdentity")
    List<Document> fromServerList(List<DocumentServerEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a client entity to a persistent entity.
     * @param entity Client entity to convert.
     * @param context Context object.
     * @return Converted persistent entity.
     */
    @Mapping(source = "entity.owner", target = "owner", qualifiedByName = "fromIdentity")
    DocumentServerEntity fromClient(Document entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps a list of client entities to a list of persistent entities.
     * @param list List of client entities.
     * @param context Context object.
     * @return Converted list of persistent entities.
     */
    //@Mapping(target = "entity.owner", qualifiedByName = "fromIdentity")
    List<DocumentServerEntity> fromClientList(List<Document> list, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a persistent entity.
     * @param entity Persistent entity to copy.
     * @param context Context object.
     * @return Copy.
     * @throws DocumentException raised if the given document cannot be copied!
     */
    DocumentServerEntity copy(DocumentServerEntity entity, @Context CycleAvoidingMappingContext context) throws DocumentException;

    /**
     * Copy a client entity.
     * @param entity Client entity to copy.
     * @param context Context object.
     * @return Copy.
     */
    Document copy(Document entity, @Context CycleAvoidingMappingContext context);

    /**
     * Converts a document owner server entity to an identity.
     * @param <T> Class that extends {@link BaseServerEntity}.
     * @param entity Document server entity.
     * @return Document server identity.
     */
    @Named("toIdentity")
    default <T extends BaseServerEntity> EntityIdentity toIdentity(final T entity)
    {
        return entity != null ? entity.getIdentity() : null;
    }

    /**
     * Converts a document owner identity to a server entity.
     * @param identity Document server identity.
     * @return Document server entity.
     */
    @Named("fromIdentity")
    default DocumentServerEntity fromIdentity(final EntityIdentity identity) throws DocumentException
    {
        if (identity != null)
        {
            ServerEntityFactory factory = new ServerEntityFactory();
            return (DocumentServerEntity) factory.create(identity);
        }

        return null;
    }
}
