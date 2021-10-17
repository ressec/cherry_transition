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
package com.hemajoo.commerce.cherry.model.document;

import com.hemajoo.commerce.cherry.model.base.entity.BaseEntity;

/**
 * Behavior of a document entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface Document extends BaseEntity
{
    /**
     * Returns the document type.
     * @return Document type.
     */
    DocumentType getDocumentType();

    /**
     * Sets the document type.
     * @param type Document type.
     */
    void setDocumentType(final DocumentType type);

    /**
     * Returns the document extension.
     * @return Document extension.
     */
    String getExtension();

    /**
     * Sets the document extension.
     * @param extension Document extension.
     */
    void setExtension(final String extension);

    /**
     * Returns the document tags.
     * @return Document tags.
     */
    String getTags();

    /**
     * Sets the document tags.
     * @param tags Document tags.
     */
    void setTags(final String tags);

    /**
     * Returns the document filename.
     * @return Document filename.
     */
    String getFilename();

    /**
     * Sets the document filename.
     * @param filename Document filename.
     */
    void setFilename(final String filename);

    /**
     * Returns the document content identifier.
     * @return Document content identifier.
     */
    String getContentId();

    /**
     * Sets the document content identifier.
     * @param contentId Document content identifier.
     */
    void setContentId(final String contentId);

    /**
     * Returns the document content length.
     * @return Document content length.
     */
    long getContentLength();

    /**
     * Sets the document content length.
     * @param contentLength Document content length.
     */
    void setContentLength(final long contentLength);

    /**
     * Returns the document mime type.
     * @return Document mime type.
     */
    String getMimeType();

    /**
     * Sets the document mime type.
     * @param mimeType Document mime type.
     */
    void setMimeType(final String mimeType);

    /**
     * Returns the document content path.
     * @return Document content path.
     */
    String getContentPath();

    /**
     * Sets the document content path.
     * @param contentPath Document content path.
     */
    void setContentPath(final String contentPath);
}
