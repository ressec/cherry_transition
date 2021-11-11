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
import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumberEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractBaseEntityMapper;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.persistence.person.mapper.AbstractPhoneNumberMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Component to convert between instances of client and server phone numbers.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class PhoneNumberConverter
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts from a server phone number entity to an entity identity.
     * @param server Server phone number entity.
     * @return Entity identity.
     */
    public EntityIdentity fromServerToIdentity(ServerPhoneNumberEntity server)
    {
        return AbstractPhoneNumberMapper.INSTANCE.fromServerToIdentity(server, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from an entity identity to a server phone number entity.
     * @param identity Entity identity.
     * @return Server phone number entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public ServerPhoneNumberEntity fromIdentityToServer(EntityIdentity identity) throws EntityException
    {
        return AbstractBaseEntityMapper.INSTANCE.map(identity,entityManager);
    }

    /**
     * Converts from a client phone number entity to a server phone number entity.
     * @param client Client phone number entity.
     * @return Server phone number entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public ServerPhoneNumberEntity fromClientToServer(ClientPhoneNumberEntity client) throws EntityException
    {
        return AbstractPhoneNumberMapper.INSTANCE.fromClientToServer(client, new CycleAvoidingMappingContext(), entityManager);
    }

    /**
     * Converts from a server phone number entity to a client phone number entity.
     * @param server Server phone number entity.
     * @return Client phone number entity.
     */
    public ClientPhoneNumberEntity fromServerToClient(ServerPhoneNumberEntity server)
    {
        return AbstractPhoneNumberMapper.INSTANCE.fromServerToClient(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a server phone number entity.
     * @param server Server phone number entity.
     * @return Copied server phone number entity.
     */
    public static ServerPhoneNumberEntity copy(ServerPhoneNumberEntity server)
    {
        return AbstractPhoneNumberMapper.INSTANCE.copy(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a client phone number entity.
     * @param client Client phone number entity.
     * @return Copied client phone number entity.
     */
    public static ClientPhoneNumberEntity copy(ClientPhoneNumberEntity client)
    {
        return AbstractPhoneNumberMapper.INSTANCE.copy(client, new CycleAvoidingMappingContext());
    }
}
