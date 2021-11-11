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
package com.hemajoo.commerce.cherry.rest.config;

import com.hemajoo.commerce.cherry.commons.exception.ContentStoreException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@code Spring} configuration containing definitions for the persistence layer for the {@code test} environment.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Configuration
@ComponentScan(basePackages = "com.hemajoo.commerce.cherry.persistence")
@EnableJpaRepositories(basePackages = "com.hemajoo.commerce.cherry.persistence")
@EntityScan(basePackages = "com.hemajoo.commerce.cherry.persistence")
@EnableFilesystemStores(basePackages = "com.hemajoo.commerce.cherry.persistence")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "auditingDateTimeProvider")
public class PersistenceConfiguration
{
    @Getter
    @Value("${hemajoo.commerce.cherry.store.location}")
    private String baseContentStoreLocation;

    @Bean(name = "auditorProvider")
    public AuditorAware<String> auditorProvider()
    {
        return new JpaAuditor();
    }

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider()
    {
        return () -> Optional.of(LocalDateTime.now());
    }

    /**
     * Base file base path for the content store.
     * @return File base path.
     * @throws ContentStoreException Raised if required content store properties are not defined!
     */
    @Bean
    public File fileSystemRoot() throws ContentStoreException
    {
        if (baseContentStoreLocation == null || baseContentStoreLocation.isEmpty())
        {
            throw new ContentStoreException("Property: 'hemajoo.commerce.cherry.store.location' cannot be null or empty!");
        }

        // Clear the content store for a test environment
        try
        {
            Arrays.stream(
                    Objects.requireNonNull(
                            new File(baseContentStoreLocation).listFiles())).forEach(File::delete);
        }
        catch (Exception e)
        {
            // Directory does not exist, do nothing!
        }

        return new File(baseContentStoreLocation);
    }

    /**
     * Returns the content store file system resource loader.
     * @return File system resource loader.
     * @throws ContentStoreException Raised if required content store properties are not defined!
     */
    @Bean
    FileSystemResourceLoader fileSystemResourceLoader() throws ContentStoreException
    {
        return new FileSystemResourceLoader(fileSystemRoot().getAbsolutePath());
    }
}
