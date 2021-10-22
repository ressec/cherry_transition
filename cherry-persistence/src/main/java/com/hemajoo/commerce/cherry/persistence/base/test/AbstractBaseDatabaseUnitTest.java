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
package com.hemajoo.commerce.cherry.persistence.base.test;

import com.hemajoo.commerce.cherry.persistence.content.DocumentStore;
import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentRepository;
import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import lombok.Getter;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.ressec.avocado.core.junit.AbstractBaseUnitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base unit test for database test classes.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractBaseDatabaseUnitTest extends AbstractBaseUnitTest
{
    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(AbstractBaseDatabaseUnitTest.class.getName());

    /**
     * Is the database initialized?
     */
    private boolean isDatabaseInitialized = false;

    /**
     * Person persistence service.
     */
    @Autowired
    protected PersonService personService;

    /**
     * {@link ServerEmailAddressEntity} service.
     */
    @Autowired
    protected EmailAddressService emailAddressService;

    /**
     * Document persistence service.
     */
    @Autowired
    protected DocumentService documentService;

    /**
     * Document content store.
     */
    @Autowired
    protected DocumentStore documentStore;

    /**
     * Document repository.
     */
    @Autowired
    protected DocumentRepository documentRepository;

    @Getter
    @Value("${hemajoo.commerce.cherry.store.location}")
    protected String baseContentStoreLocation;

    /**
     * Database data source.
     */
    @Value("${spring.datasource.url}")
    private String datasource;

    /**
     * Database username.
     */
    @Value("${spring.datasource.username}")
    private String user;

    /**
     * Database user password.
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Database {@code Flyway} locations.
     */
    @Value("${spring.flyway.locations}")
    private String locations;

    /**
     * {@code Flyway} mode.
     */
    @Value("${spring.flyway.enabled}")
    private boolean enabled;

    /**
     * Database default schema to use for the tests.
     */
    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String schemas;

    protected void initializeDatabase()
    {
        LOG.log(Level.INFO, ()
                -> String.format("Property key: %s, value: %s", "spring.jpa.properties.hibernate.default_schema", schemas));

        if (enabled && !(isDatabaseInitialized))
        {
            // Ensure the cherry H2 db is available and up to date for the tests.
            Flyway flyway = Flyway.configure()
                    .dataSource(datasource,user, password)
                    .schemas(schemas)
                    .locations(locations)
                    .load();

            flyway.clean();
            flyway.migrate();

            isDatabaseInitialized = true;
    }
    }

    @BeforeEach
    protected void setUp()
    {
        initializeDatabase();
    }
}
