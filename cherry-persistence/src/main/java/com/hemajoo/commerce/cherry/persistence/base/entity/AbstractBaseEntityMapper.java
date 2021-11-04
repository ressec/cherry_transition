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

package com.hemajoo.commerce.cherry.persistence.base.entity;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityManager;

/**
 * Base mapper to convert between instances of entity identities to server entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper
public abstract class AbstractBaseEntityMapper
{
    /**
     * Instance to this bean mapper.
     */
    public static final AbstractBaseEntityMapper INSTANCE = Mappers.getMapper(AbstractBaseEntityMapper.class);

    /**
     * Maps an entity identity to a server base entity.
     * <hr>
     * If the base entity exist in the underlying database, it will be loaded and returned, otherwise an exception is raised.
     * @param identity Entity identity.
     * @param entityManager Entity manager.
     * @return Server base entity.
     * @throws EntityException Thrown in case an error occurred while trying to retrieve the entity from the underlying database.
     */
    public <T extends ServerBaseEntity> T map(EntityIdentity identity, @Context EntityManager entityManager) throws EntityException
    {
        ServerBaseEntity entity;

        if (identity != null)
        {
            entity = entityManager.find(ServerBaseEntity.class, identity.getId());
            if (entity == null)
            {
                throw new EntityException(identity.getEntityType(), String.format("Server entity with identity: %s cannot be found!", identity));
            }

            return (T) entity;
        }

        return null;
    }
}

