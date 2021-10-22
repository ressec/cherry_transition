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
package com.hemajoo.commerce.cherry.persistence.person.converter;

import com.hemajoo.commerce.cherry.model.person.entity.ClientPersonEntity;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.mapper.PersonMapper;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * Utility class to convert between client person and server person entities and vice-versa.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PersonConverter
{
    /**
     * Converts a client entity to a persistent entity.
     * @param entity Client entity to map.
     * @return Mapped persistent entity.
     */
    public static ServerPersonEntity convertClient(ClientPersonEntity entity)
    {
        return PersonMapper.INSTANCE.mapClient(entity, new CycleAvoidingMappingContext());
    }

    /**
     * Converts a list of client entities to a list of persistent entities.
     * @param list List of client entities to map.
     * @return Mapped list of persistent entities.
     */
    public static List<ServerPersonEntity> convertClientList(List<ClientPersonEntity> list)
    {
        return PersonMapper.INSTANCE.mapClientList(list, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from a (persistent) email address to a (client) email address.
     * @param entity Persistent entity to map.
     * @return Mapped client entity.
     */
    public static ClientPersonEntity convertServer(ServerPersonEntity entity)
    {
        return PersonMapper.INSTANCE.mapServer(entity, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from a list of persistent entities to a list of client entities.
     * @param list List of persistent entities to map.
     * @return Mapped list of client entities.
     */
    public static List<ClientPersonEntity> convertServerList(List<ServerPersonEntity> list)
    {
        return PersonMapper.INSTANCE.mapServerList(list, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a persistent entity.
     * @param entity Persistent source entity to copy.
     * @return Copied persistent entity.
     */
    public static ServerPersonEntity copy(ServerPersonEntity entity)
    {
        return PersonMapper.INSTANCE.copy(entity, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a client entity.
     * @param entity Client source entity to copy.
     * @return Copied client entity.
     */
    public static ClientPersonEntity copy(ClientPersonEntity entity)
    {
        return PersonMapper.INSTANCE.copy(entity, new CycleAvoidingMappingContext());
    }
}
