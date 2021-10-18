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
package com.hemajoo.commerce.cherry.persistence.base.specification;

import com.hemajoo.commerce.cherry.model.base.search.criteria.SearchCriteria;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a generic specification that integrates search criteria to be applied to a {@code JPA} repository to retrieve records matching the given
 * set of search criteria.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public final class GenericSpecification<T> implements Specification<T>
{
    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 1900581010229669687L;

    /**
     * List of search criteria.
     */
    private final List<SearchCriteria> list = new ArrayList<>();

    @Override
    public Predicate toPredicate(@NotNull Root<T> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder builder)
    {
        List<Predicate> predicates = new ArrayList<>();

        // Substitute special characters before processing criteria!
        substituteSpecialCharacters();

        // Add criteria to list of predicates.
        for (SearchCriteria criteria : list)
        {
            switch (criteria.getOperation())
            {
                case EQUAL:
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case MATCH:
                    predicates.add(builder.like(
                            builder.lower(root.get(criteria.getKey())),
                            criteria.getValue().toString().toLowerCase()));
                    break;
                case LESS_THAN:
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case MATCH_END:
                    predicates.add(builder.like(
                            builder.lower(root.get(criteria.getKey())),
                            criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case NOT_EQUAL:
                    predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case GREATER_THAN:
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case MATCH_BEFORE:
                    predicates.add(builder.like(
                            builder.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase()));
                    break;
                case MATCH_BETWEEN:
                    predicates.add(builder.like(
                            builder.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    break;
                case LESS_THAN_EQUAL:
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case GREATER_THAN_EQUAL:
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case EQUAL_OBJECT_UUID:
                    predicates.add(builder.equal(root.get(criteria.getKey()).get("id"), UUID.fromString(criteria.getValue().toString())));
                    break;
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * Adds a search criteria to the list.
     * @param criteria Criteria to add.
     */
    public void add(SearchCriteria criteria)
    {
        list.add(criteria);
    }

    public int count()
    {
        return list.size();
    }

    /**
     * Substitutes special characters from the list of received criteria.
     */
    public void substituteSpecialCharacters()
    {
        String value;

        for (SearchCriteria criteria : list)
        {
            if (criteria.getValue() instanceof String)
            {
                value = (String) criteria.getValue();
                value = value.replace('*', '%');
                criteria.setValue(value);
            }
        }
    }
}
