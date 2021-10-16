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
package com.hemajoo.commerce.cherry.persistence.base.validation;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.Map;

/**
 * Hibernate validator customizer.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public class ValidatorHibernateCustomizer implements HibernatePropertiesCustomizer
{
    // Necessary when using constraint validators with SpringBootTest
    // see: https://stackoverflow.com/questions/62985187/getting-nullpointerexception-for-repository-while-using-custom-javax-validator

    /**
     * Validator provider.
     */
    private final ObjectProvider<Validator> provider;

    @Autowired
    public ValidatorHibernateCustomizer(ObjectProvider<Validator> provider)
    {
        this.provider = provider;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties)
    {
        var validator = provider.getIfUnique();
        if (validator != null)
        {
            hibernateProperties.put("javax.persistence.validation.factory", validator);
        }
    }
}
