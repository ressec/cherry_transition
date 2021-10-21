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

import com.hemajoo.commerce.cherry.commons.entity.Identity;
import com.hemajoo.commerce.cherry.model.document.ClientDocumentEntity;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractServerBaseEntity;
import com.hemajoo.commerce.cherry.persistence.base.factory.ServerEntityFactory;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * A mapper interface providing services to map between {@link ClientDocumentEntity} and {@link ServerDocumentEntity} and vice-versa.
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
    ClientDocumentEntity mapPersistence(ServerDocumentEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps a list of persistent entities to a list of client entities.
     * @param list List of persistent entities.
     * @param context Context object.
     * @return Converted list of client entities.
     */
    List<ClientDocumentEntity> mapPersistenceList(List<ServerDocumentEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a client entity to a persistent entity.
     * @param entity Client entity to convert.
     * @param context Context object.
     * @return Converted persistent entity.
     */
    @Mapping(source = "entity.owner", target = "owner", qualifiedByName = "fromIdentity")
    ServerDocumentEntity mapClient(ClientDocumentEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps a list of client entities to a list of persistent entities.
     * @param list List of client entities.
     * @param context Context object.
     * @return Converted list of persistent entities.
     */
    List<ServerDocumentEntity> mapClientList(List<ClientDocumentEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a persistent entity.
     * @param entity Persistent entity to copy.
     * @param context Context object.
     * @return Copy.
     * @throws DocumentException raised if the given document cannot be copied!
     */
    ServerDocumentEntity copy(ServerDocumentEntity entity, @Context CycleAvoidingMappingContext context) throws DocumentException;

    /**
     * Copy a client entity.
     * @param entity Client entity to copy.
     * @param context Context object.
     * @return Copy.
     */
    ClientDocumentEntity copy(ClientDocumentEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Converts a document owner server entity to an identity.
     * @param entity Document server entity.
     * @return Document server identity.
     */
    @Named("toIdentity")
//    default <T extends BaseServerEntity> EntityIdentity toIdentity(final T entity)
    default Identity toIdentity(final AbstractServerBaseEntity entity) // TODO Should have as parameter a ServerEntity interface instead of a BaseServerEntity class!
    {
        return entity != null ? entity.getIdentity() : null;
    }

    /**
     * Converts a document owner identity to a server entity.
     * @param identity Document server identity.
     * @return Document server entity.
     */
    @Named("fromIdentity")
    default ServerDocumentEntity fromIdentity(final /*EntityIdentity*/ Identity identity) throws DocumentException // TODO Should return a ServerDocument interface instead of a DocumentServerEntity class!
    {
//        return (ServerDocumentEntity) ServerEntityFactory.from(identity);
        if (identity != null)
        {
            ServerEntityFactory factory = new ServerEntityFactory();
            return (ServerDocumentEntity) factory.from(identity);
        }

        return null;
    }
}
