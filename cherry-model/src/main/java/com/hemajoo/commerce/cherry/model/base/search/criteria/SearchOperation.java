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
package com.hemajoo.commerce.cherry.model.base.search.criteria;

public enum SearchOperation
{
    GREATER_THAN,

    LESS_THAN,

    GREATER_THAN_EQUAL,

    LESS_THAN_EQUAL,

    NOT_EQUAL,

    EQUAL,

    MATCH,

    MATCH_BETWEEN,

    MATCH_BEFORE,

    MATCH_END,

    EQUAL_OBJECT_UUID
}
