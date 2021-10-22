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
package com.hemajoo.commerce.cherry.model.person.entity.base;

import com.hemajoo.commerce.cherry.model.base.entity.BaseEntity;
import com.hemajoo.commerce.cherry.model.person.type.GenderType;
import com.hemajoo.commerce.cherry.model.person.type.PersonType;

import java.util.Date;

/**
 * Interface providing the behavior of a person entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface Person extends BaseEntity
{
    /**
     * Returns the person last name.
     * @return Last name.
     */
    String getLastName();

    /**
     * Sets the person last name.
     * @param lastName Last name.
     */
    void setLastName(final String lastName);

    /**
     * Returns the person first name.
     * @return First name.
     */
    String getFirstName();

    /**
     * Sets the person first name.
     * @param firstName First name.
     */
    void setFirstName(final String firstName);

    /**
     * Returns the person birthdate.
     * @return Birthdate.
     */
    Date getBirthDate();

    /**
     * Sets the person birthdate.
     * @param birthDate Birthdate.
     */
    void setBirthDate(final Date birthDate);

    /**
     * Returns the person type.
     * @return Person type.
     */
    PersonType getPersonType();

    /**
     * Sets the person type.
     * @param type Person type.
     */
    void setPersonType(final PersonType type);

    /**
     * Returns the person gender type.
     * @return Gender type.
     */
    GenderType getGenderType();

    /**
     * Sets the person gender type.
     * @param type Gender type.
     */
    void setGenderType(final GenderType type);
}
