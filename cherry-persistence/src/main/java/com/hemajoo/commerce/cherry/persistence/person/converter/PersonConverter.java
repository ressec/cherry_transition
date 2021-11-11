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

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.model.person.entity.ClientPersonEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractBaseEntityMapper;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.mapper.AbstractPersonMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Component to convert between instances of client and server persons.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class PersonConverter
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts from a server person entity to an entity identity.
     * @param server Server person entity.
     * @return Entity identity.
     */
    public EntityIdentity fromServerToIdentity(ServerPersonEntity server)
    {
        return AbstractPersonMapper.INSTANCE.fromServerToIdentity(server, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from an entity identity to a server person entity.
     * @param identity Entity identity.
     * @return Server person entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public ServerPersonEntity fromIdentityToServer(EntityIdentity identity) throws EntityException
    {
        return AbstractBaseEntityMapper.INSTANCE.map(identity,entityManager);
    }

    /**
     * Converts from a client person entity to a server person entity.
     * @param client Client person entity.
     * @return Server person entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public ServerPersonEntity fromClientToServer(ClientPersonEntity client) throws EntityException
    {
        return AbstractPersonMapper.INSTANCE.fromClientToServer(client, new CycleAvoidingMappingContext(), entityManager);
    }

    /**
     * Converts from a server person entity to a client person entity.
     * @param server Server person entity.
     * @return Client person entity.
     */
    public ClientPersonEntity fromServerToClient(ServerPersonEntity server)
    {
        return AbstractPersonMapper.INSTANCE.fromServerToClient(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a server person entity.
     * @param server Server person entity.
     * @return Copied server person entity.
     */
    public static ServerPersonEntity copy(ServerPersonEntity server)
    {
        return AbstractPersonMapper.INSTANCE.copy(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a client person entity.
     * @param client Client person entity.
     * @return Copied client person entity.
     */
    public static ClientPersonEntity copy(ClientPersonEntity client)
    {
        return AbstractPersonMapper.INSTANCE.copy(client, new CycleAvoidingMappingContext());
    }
}
