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
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.content.s3.S3ObjectId;
import org.springframework.content.s3.config.EnableS3Stores;
import org.springframework.content.s3.config.S3StoreConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Configuration containing definitions for the persistence layer.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Configuration
@ComponentScan(basePackages = { "com.hemajoo.commerce.cherry.persistence" } )
@EnableJpaRepositories(basePackages = { "com.hemajoo.commerce.cherry.persistence" })
@EntityScan(basePackages = "com.hemajoo.commerce.cherry.persistence")
@EnableFilesystemStores(basePackages = "com.hemajoo.commerce.cherry.persistence")
@EnableS3Stores(basePackages = "com.hemajoo.commerce.cherry.persistence")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "auditingDateTimeProvider")
public class PersistenceConfiguration
{
    @Getter
    @Value("${hemajoo.commerce.cherry.store.location}")
    private String baseContentStoreLocation;

    /**
     * Amazon S3 client when using a S3 document store.
     * @return {@link S3Client}.
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.content.storage", name = "type", havingValue = "s3")
    public S3Client client()
    {
        Region region = Region.EU_WEST_3;

        return S3Client.builder()
                .region(region)
                .build();
    }

    /**
     * Amazon S3 client when using a S3 document store.
     * @return {@link S3Client}.
     */
    @Bean
    public S3StoreConfigurer configure()
    {
        return registry -> registry.addConverter(new Converter<ServerDocumentEntity, S3ObjectId>()
        {
            @Override
            public S3ObjectId convert(ServerDocumentEntity source)
            {
                return new S3ObjectId("ressec-private", source.getId().toString());
                //                        return new S3ObjectId(entity.getCustomBucketField(), entity.getCustomContentIdField());
            }
        });
    }

    /**
     * Provides the auditor implementation when saving an entity in the backend.
     * @return {@link AuditorAware}.
     */
    @Bean(name = "auditorProvider")
    public AuditorAware<String> auditorProvider()
    {
        return new JpaAuditor();
    }

    /**
     * Provides the date time implementation when saving an entity in the backend (for creation and modification properties).
     * @return {@link DateTimeProvider}.
     */
    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider()
    {
        return () -> Optional.of(LocalDateTime.now());
    }

    /**
     * File system root path to use for storing documents.
     * @return File system root path.
     * @throws ContentStoreException Thrown to indicate an error occurred when trying to retrieve the filesystem root path.
     */
    @Bean
    public File fileSystemRoot() throws ContentStoreException
    {
        if (baseContentStoreLocation == null || baseContentStoreLocation.isEmpty())
        {
            throw new ContentStoreException("Property: 'hemajoo.commerce.cherry.store.location' cannot be null or empty!");
        }

        try
        {
            // Clear the content store for a test environment
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
     * @throws ContentStoreException Thrown to indicate an error occurred when trying to access file system resource loader.
     */
    @Bean
    FileSystemResourceLoader fileSystemResourceLoader() throws ContentStoreException
    {
        return new FileSystemResourceLoader(fileSystemRoot().getAbsolutePath());
    }
}
