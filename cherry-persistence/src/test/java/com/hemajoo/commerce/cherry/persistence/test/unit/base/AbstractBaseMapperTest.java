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

import com.hemajoo.commerce.cherry.model.base.entity.BaseEntity;
import lombok.NonNull;
import org.ressec.avocado.core.junit.AbstractBaseUnitTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base entity mapper for unit tests.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractBaseMapperTest extends AbstractBaseUnitTest
{
    /**
     * Checks equality of base fields.
     * @param source Source entity.
     * @param target Target entity.
     */
    protected void checkBaseFields(final @NonNull BaseEntity source, final @NonNull BaseEntity target)
    {
        assertThat(source.getId())
                .as("Id should be equal!")
                .isEqualTo(target.getId());

        assertThat(source.getCreatedBy())
                .as("Created by should be equal!")
                .isEqualTo(target.getCreatedBy());

        assertThat(source.getCreatedDate())
                .as("Created date should be equal!")
                .isEqualTo(target.getCreatedDate());

        assertThat(source.getModifiedBy())
                .as("Modified by should be equal!")
                .isEqualTo(target.getModifiedBy());

        assertThat(source.getModifiedDate())
                .as("Modified date should be equal!")
                .isEqualTo(target.getModifiedDate());

        assertThat(source.getStatusType())
                .as("Status type should be equal!")
                .isEqualTo(target.getStatusType());

        assertThat(source.getSince())
                .as("Since should be equal!")
                .isEqualTo(target.getSince());

        assertThat(source.getEntityType())
                .as("Entity type should be equal!")
                .isEqualTo(target.getEntityType());

        assertThat(source.getDescription())
                .as("Description should be equal!")
                .isEqualTo(target.getDescription());

        assertThat(source.getReference())
                .as("Reference should be equal!")
                .isEqualTo(target.getReference());

        assertThat(source.getName())
                .as("Name should be equal!")
                .isEqualTo(target.getName());
    }
}
