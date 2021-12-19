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
package com.hemajoo.commerce.cherry.persistence.content;

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
    @Value("${hemajoo.commerce.cherry.store.type}")
    private String storeTypeName;

    /**
     * Content store repository.
     */
    @Autowired
    private FileSystemContentStore storeFileSystem;

    private String storeName;

    /**
     * Content store repository.
     */
    @Autowired(required = false)
    private S3DocumentStore storeS3;

    @SuppressWarnings("java:S3740")
    public final ContentStore getStore()
    {
        if (storeName == null || storeName.isEmpty())
        {
            computeDocumentStoreSelection();
        }

        return storeName.equals("FS") ? storeFileSystem : storeS3;
    }

    /**
     * Computes the document store selection.
     */
    private void computeDocumentStoreSelection()
    {
        if (storeS3 == null)
        {
            LOGGER.info("Using a document store of type: FileSystem");
            storeName = "FS";
        }
        else
        {
            if (storeTypeName.equals("S3"))
            {
                LOGGER.info("Using a document store of type: S3");
                storeName = "S3";
            }
            else if (storeTypeName.equals("FS"))
            {
                LOGGER.info("Using a document store of type: FileSystem");
                storeName = "FS";
            }
        }
    }
}
