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

import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import com.hemajoo.commerce.cherry.persistence.person.service.PhoneNumberService;
import com.hemajoo.commerce.cherry.persistence.person.service.PostalAddressService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Person service factory providing access to persistence services of the person domain.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public class ServiceFactoryPerson
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
}
