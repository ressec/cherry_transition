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
import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import com.hemajoo.commerce.cherry.persistence.person.service.PhoneNumberService;
import com.hemajoo.commerce.cherry.persistence.person.service.PostalAddressService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ServerEntityFactory
{
    /**
     * Person persistence service.
     */
    @Getter
    @Autowired
    private PersonService personService;

    /**
     * Document persistence service.
     */
    @Getter
    @Autowired
    private DocumentService documentService;

    /**
     * Email persistence service.
     */
    @Getter
    @Autowired
    private EmailAddressService emailAddressService;

    /**
     * Phone number persistence service.
     */
    @Getter
    @Autowired
    private PhoneNumberService phoneNumberService;

    /**
     * Postal address persistence service.
     */
    @Getter
    @Autowired
    private PostalAddressService postalAddressService;

    //    public final <T extends ServerEntity> T from(final Identity identity) // TODO Try to transform to a static method
    public final ServerBaseEntity from(final Identity identity) throws EntityException
//    public static ServerBaseEntity from(final Identity identity)
    {
        if (identity != null)
        {
            switch (identity.getEntityType())
            {
                case DOCUMENT:
                    return documentService.findById(identity.getId());

                case PERSON:
                    if (!personService.existId(identity.getId()))
                    {
                        throw new EntityException(EntityType.PERSON, String.format("%s does not exist!", identity));
                    }
                    return personService.findById(identity.getId());

                case EMAIL_ADDRESS:
                    return emailAddressService.findById(identity.getId());

                case PHONE_NUMBER:
                    return phoneNumberService.findById(identity.getId());

                case POSTAL_ADDRESS:
                    return postalAddressService.findById(identity.getId());

                default:
                    throw new EntityException(EntityType.UNKNOWN, "Unhandled entity type for: " + identity, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return null;
    }
}
