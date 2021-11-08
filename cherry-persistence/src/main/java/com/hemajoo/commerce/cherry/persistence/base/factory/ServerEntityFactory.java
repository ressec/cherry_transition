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
package com.hemajoo.commerce.cherry.persistence.base.factory;

import com.hemajoo.commerce.cherry.commons.entity.Identity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ServerEntityFactory
{
    /**
     * Persistence services of the person domain.
     */
    @Getter
    @Autowired
    private ServiceFactoryPerson services;

    /**
     * Creates a server entity given an entity identity.
     * @param identity Entity identity.
     * @return Server entity.
     * @throws EntityException Thrown to indicate an error occurred while trying to create a server entity.
     */
    public final ServerBaseEntity from(final Identity identity) throws EntityException
    {
        if (identity != null)
        {
            switch (identity.getEntityType())
            {
                case DOCUMENT:
                    return services.getDocumentService().findById(identity.getId());

                case PERSON:
                    if (!services.getPersonService().existId(identity.getId()))
                    {
                        throw new EntityException(EntityType.PERSON, String.format("%s does not exist!", identity));
                    }
                    return services.getPersonService().findById(identity.getId());

                case EMAIL_ADDRESS:
                    return services.getEmailAddressService().findById(identity.getId());

                case PHONE_NUMBER:
                    return services.getPhoneNumberService().findById(identity.getId());

                case POSTAL_ADDRESS:
                    return services.getPostalAddressService().findById(identity.getId());

                default:
                    throw new EntityException(EntityType.UNKNOWN, "Unhandled entity type for: " + identity, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return null;
    }
}
