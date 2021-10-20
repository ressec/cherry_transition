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
package com.hemajoo.commerce.cherry.model.base.converter;

import com.hemajoo.commerce.cherry.commons.entity.Identity;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing services to convert entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class GenericEntityConverter
{
    /**
     * Converts a list of entities (client or server ones) to a list of UUIDs.
     * @param source List of entities.
     * @return List of UUIDs.
     */
    public static List<String> toIdList(final @NonNull List<? extends Identity> source)
    {
        return source.stream()
                .map(e -> e.getId().toString())
                .collect(Collectors.toList());
    }
}
