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

import com.hemajoo.commerce.cherry.commons.entity.IEntityIdentity;
import com.hemajoo.commerce.cherry.persistence.base.entity.BaseServerEntity;
import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerEntityFactory
{
    /**
     * Person persistence service.
     */
    @Autowired
    private PersonService personService;

    /**
     * Document persistence service.
     */
    @Autowired
    private DocumentService documentService;

    /**
     * Email persistence service.
     */
    @Autowired
    private EmailAddressService emailAddressService;

    public BaseServerEntity create(final @NonNull IEntityIdentity identity)
    {
        switch (identity.getEntityType())
        {
            case DOCUMENT:
                return documentService.findById(identity.getId());

            case PERSON:
                return personService.findById(identity.getId());

            case EMAIL_ADDRESS:
                return emailAddressService.findById(identity.getId());

            default:
                System.err.println("Unhandled entity type: " + identity.getEntityType());
        }

        return null; // TODO Thrown an exception!
    }
}
