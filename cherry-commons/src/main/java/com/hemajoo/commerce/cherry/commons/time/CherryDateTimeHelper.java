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
package com.hemajoo.commerce.cherry.commons.time;

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class CherryDateTimeHelper
{
    /**
     * Returns a UTC local date time with precision set to milliseconds (6 digits)
     * @return Current UTC local date time.
     */
    public static LocalDateTime now()
    {
        return LocalDateTime.now(Clock.tickMillis(ZoneOffset.UTC));
    }
}
