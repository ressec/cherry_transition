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
package com.hemajoo.commerce.cherry.persistence.test.unit.base;

import com.hemajoo.commerce.cherry.model.base.entity.BaseClientEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.BaseServerEntity;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.ressec.avocado.core.junit.BaseUnitTest;

/**
 * Base mapper unit test.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class BaseMapperTest extends BaseUnitTest
{
    /**
     * Checks equality of base fields.
     * @param persistent Base persistent entity.
     * @param client Base client entity.
     */
    protected void checkBaseFields(final @NonNull BaseServerEntity persistent, final @NonNull BaseClientEntity client)
    {
        Assertions.assertEquals(persistent.getId(), client.getId());
        Assertions.assertEquals(persistent.getCreatedBy(), client.getCreatedBy());
        Assertions.assertEquals(persistent.getCreatedDate(), client.getCreatedDate());
        Assertions.assertEquals(persistent.getModifiedBy(), client.getModifiedBy());
        Assertions.assertEquals(persistent.getModifiedDate(), client.getModifiedDate());
        Assertions.assertEquals(persistent.getStatusType(), client.getStatusType());
        Assertions.assertEquals(persistent.getSince(), client.getSince());
        Assertions.assertEquals(persistent.getEntityType(), client.getEntityType());
        Assertions.assertEquals(persistent.getDescription(), client.getDescription());
        Assertions.assertEquals(persistent.getReference(), client.getReference());
        Assertions.assertEquals(persistent.getName(), client.getName());
    }

    /**
     * Checks equality of base fields.
     * @param persistent Base persistent entity.
     * @param copy Base persistent entity.
     */
    protected void checkBaseFields(final @NonNull BaseServerEntity persistent, final @NonNull BaseServerEntity copy)
    {
        Assertions.assertEquals(persistent.getId(), copy.getId());
        Assertions.assertEquals(persistent.getCreatedBy(), copy.getCreatedBy());
        Assertions.assertEquals(persistent.getCreatedDate(), copy.getCreatedDate());
        Assertions.assertEquals(persistent.getModifiedBy(), copy.getModifiedBy());
        Assertions.assertEquals(persistent.getModifiedDate(), copy.getModifiedDate());
        Assertions.assertEquals(persistent.getStatusType(), copy.getStatusType());
        Assertions.assertEquals(persistent.getSince(), copy.getSince());
        Assertions.assertEquals(persistent.getEntityType(), copy.getEntityType());
        Assertions.assertEquals(persistent.getDescription(), copy.getDescription());
        Assertions.assertEquals(persistent.getReference(), copy.getReference());
        Assertions.assertEquals(persistent.getName(), copy.getName());
    }

    /**
     * Checks equality of base fields.
     * @param client Base client entity.
     * @param copy Base client entity.
     */
    protected void checkBaseFields(final @NonNull BaseClientEntity client, final @NonNull BaseClientEntity copy)
    {
        Assertions.assertEquals(client.getId(), copy.getId());
        Assertions.assertEquals(client.getCreatedBy(), copy.getCreatedBy());
        Assertions.assertEquals(client.getCreatedDate(), copy.getCreatedDate());
        Assertions.assertEquals(client.getModifiedBy(), copy.getModifiedBy());
        Assertions.assertEquals(client.getModifiedDate(), copy.getModifiedDate());
        Assertions.assertEquals(client.getStatusType(), copy.getStatusType());
        Assertions.assertEquals(client.getSince(), copy.getSince());
        Assertions.assertEquals(client.getEntityType(), copy.getEntityType());
        Assertions.assertEquals(client.getDescription(), copy.getDescription());
        Assertions.assertEquals(client.getReference(), copy.getReference());
        Assertions.assertEquals(client.getName(), copy.getName());
    }
}
