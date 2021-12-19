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
package com.hemajoo.commerce.cherry.persistence.document.content;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ProxyContentStore
{
    @Getter
    @Value("${spring.content.storage.type}")
    private String springContentStoreType;

    /**
     * Content store type to use.
     */
    private ContentStoreType storeType = ContentStoreType.UNKNOWN;

    /**
     * Content store repository.
     */
    @Autowired
    private FileSystemDocumentStore storeFileSystem;

    /**
     * Content store repository.
     */
    @Autowired(required = false)
    private S3DocumentStore storeS3;

    @SuppressWarnings("java:S3740")
    public final ContentStore getStore()
    {
        if (storeType == ContentStoreType.UNKNOWN)
        {
            computeDocumentStoreSelection();
        }

        return storeType == ContentStoreType.FILESYSTEM ? storeFileSystem : storeS3;
    }

    /**
     * Computes the document store type to use according to the configuration.
     */
    private void computeDocumentStoreSelection()
    {
        // If not set in configuration, then defaulting to FileSystem.
        if (springContentStoreType == null)
        {
            LOGGER.info("Defaulting to a content store type: FileSystem");
            storeType = ContentStoreType.FILESYSTEM;
        }
        else
        {
            if (springContentStoreType.equals("s3"))
            {
                LOGGER.info("Using a content store of type: S3");
                storeType = ContentStoreType.S3;
            }
            else if (springContentStoreType.equals("filesystem"))
            {
                LOGGER.info("Using a content store of type: FileSystem");
                storeType = ContentStoreType.FILESYSTEM;
            }
        }
    }
}
