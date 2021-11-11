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
import com.hemajoo.commerce.cherry.model.person.entity.ClientPostalAddressEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractBaseEntityMapper;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPostalAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.mapper.AbstractPostalAddressMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Component to convert between instances of client and server postal addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class PostalAddressConverter
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts from a server postal address entity to an entity identity.
     * @param server Server postal address entity.
     * @return Entity identity.
     */
    public EntityIdentity fromServerToIdentity(ServerPostalAddressEntity server) throws EntityException
    {
        return AbstractPostalAddressMapper.INSTANCE.fromServerToIdentity(server, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from an entity identity to a server postal address entity.
     * @param identity Entity identity.
     * @return Server postal address entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public ServerPostalAddressEntity fromIdentityToServer(EntityIdentity identity) throws EntityException
    {
        return AbstractBaseEntityMapper.INSTANCE.map(identity,entityManager);
    }

    /**
     * Converts from a client postal address entity to a server postal address entity.
     * @param client Client postal address entity.
     * @return Server postal address entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public ServerPostalAddressEntity fromClientToServer(ClientPostalAddressEntity client) throws EntityException
    {
        return AbstractPostalAddressMapper.INSTANCE.fromClientToServer(client, new CycleAvoidingMappingContext(), entityManager);
    }

    /**
     * Converts from a server email address entity to a client email address entity.
     * @param server Server postal address entity.
     * @return Client postal address entity.
     */
    public ClientPostalAddressEntity fromServerToClient(ServerPostalAddressEntity server)
    {
        return AbstractPostalAddressMapper.INSTANCE.fromServerToClient(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a server email address entity.
     * @param server Server postal address entity.
     * @return Copied server postal address entity.
     */
    public static ServerPostalAddressEntity copy(ServerPostalAddressEntity server)
    {
        return AbstractPostalAddressMapper.INSTANCE.copy(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a client email address entity.
     * @param client Client postal address entity.
     * @return Copied client postal address entity.
     */
    public static ClientPostalAddressEntity copy(ClientPostalAddressEntity client)
    {
        return AbstractPostalAddressMapper.INSTANCE.copy(client, new CycleAvoidingMappingContext());
    }
}
