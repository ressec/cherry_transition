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

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.entity.BaseEntity;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a server base entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
//@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ENTITY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ServerBaseEntity extends AbstractStatusServerEntity implements BaseEntity, ServerEntity
{
    public static final String FIELD_ID             = "id";
    public static final String FIELD_ENTITY_TYPE    = "entityType";
    public static final String FIELD_NAME           = "name";
    public static final String FIELD_DESCRIPTION    = "description";
    public static final String FIELD_REFERENCE      = "reference";

    /**
     * Entity identifier.
     */
    @Getter
    @Setter
    @Id
    @Type(type = "uuid-char") // Allow displaying in the DB the UUID as a string instead of a binary field!
    @GeneratedValue
    private UUID id;

    /**
     * Entity type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ENTITY_TYPE", length = 50)
    private EntityType entityType;

    /**
     * Entity name.
     */
    @Getter
    @Setter
    @Column(name = "NAME")
    private String name;

    /**
     * Entity description.
     */
    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Entity internal reference.
     */
    @Getter
    @Setter
    @Column(name = "REFERENCE", length = 100)
    private String reference;

    /**
     * Documents associated with this entity.
     */
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ServerDocumentEntity> documents = new ArrayList<>();

    /**
     * Creates a new base entity.
     * @param type Entity type.
     */
    protected ServerBaseEntity(final EntityType type)
    {
        this.entityType = type;
    }

    /**
     * Adds a document to this entityDocumentEntity.
     * @param document Document.
     */
    public final void addDocument(final @NonNull ServerDocumentEntity document)
    {
        documents.add(document);
        document.setOwner(this);
    }

    /**
     * Returns the documents associated to this entity.
     * @return List of documents.
     */
    public List<ServerDocumentEntity> getDocuments()
    {
        if (entityType == EntityType.MEDIA)
        {
            return new ArrayList<>();
        }

        return documents;
    }

    @Override
    public final EntityIdentity getIdentity()
    {
        return new EntityIdentity(id, entityType);
    }
}
