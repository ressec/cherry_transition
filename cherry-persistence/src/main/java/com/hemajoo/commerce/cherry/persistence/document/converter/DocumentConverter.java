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
package com.hemajoo.commerce.cherry.persistence.document.converter;

import com.hemajoo.commerce.cherry.model.document.ClientDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.mapper.DocumentMapper;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * Utility class to convert {@link ClientDocumentEntity} to {@link ServerDocumentEntity} and vice-versa.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class DocumentConverter
{
    /**
     * Converts a persistent entity to a client entity.
     * @param entity Client entity to map.
     * @return Mapped persistent entity.
     */
    public static ServerDocumentEntity fromClient(ClientDocumentEntity entity)
    {
        return DocumentMapper.INSTANCE.mapClient(entity, new CycleAvoidingMappingContext());
    }

    /**
     * Converts a list of client entities to a list of persistent entities.
     * @param list List of client entities to map.
     * @return Converted list of persistent entities.
     */
    public static List<ServerDocumentEntity> fromClientList(List<ClientDocumentEntity> list)
    {
        return DocumentMapper.INSTANCE.mapClientList(list, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from a persistent entity to a client entity.
     * @param entity Persistent entity to map.
     * @return Mapped client entity.
     */
    public static ClientDocumentEntity fromServer(ServerDocumentEntity entity)
    {
        return DocumentMapper.INSTANCE.mapPersistence(entity, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from a list of persistent entities to a list of client entities.
     * @param list List of persistent entities to map.
     * @return Mapped list of client entities.
     */
    public static List<ClientDocumentEntity> fromServerList(List<ServerDocumentEntity> list)
    {
        return DocumentMapper.INSTANCE.mapPersistenceList(list, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a persistent entity.
     * @param entity Persistent source entity to copy.
     * @return Copied persistent entity.
     */
    public static ServerDocumentEntity copy(ServerDocumentEntity entity)
    {
        return DocumentMapper.INSTANCE.copy(entity, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a client entity.
     * @param entity Client source entity to copy.
     * @return Copied client entity.
     */
    public static ClientDocumentEntity copy(ClientDocumentEntity entity)
    {
        return DocumentMapper.INSTANCE.copy(entity, new CycleAvoidingMappingContext());
    }
}
