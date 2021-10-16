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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.entity.BaseClientEntity;
import lombok.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.ressec.avocado.core.helper.FileHelper;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Represents a document client entity.
 * <hr>
 * This represents a document that is used by client applications.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Document extends BaseClientEntity
{
    /**
     * Document type.
     */
    @Getter
    @Setter
    private transient DocumentType documentType;

    /**
     * Document file extension.
     */
    @Getter
    @Setter
    private String extension;

    /**
     * Document tags.
     */
    @Getter
    @Setter
    private String tags;

    /**
     * Document file name.
     */
    @Getter
    @Setter
    private String filename;

    /**
     * File content identifier.
     * <br>
     * UUID of the file in the content store.
     */
    @Getter
    @Setter
    private String contentId;

    /**
     * File content length.
     */
    @Getter
    @Setter
    private long contentLength;

    /**
     * File MIME type.
     */
    @Getter
    @Setter
    private String mimeType = "text/plain";

    /**
     * File path (in the content store).
     */
    @Getter
    @Setter
    private String contentPath;

    /**
     * Document owner identity.
     */
    @Getter
    @Setter
    private EntityIdentity owner;

    //@Transient
    @ToString.Exclude
    @Getter
    @JsonIgnore
    private transient InputStream content;

    /**
     * Multipart file.
     */
    @EqualsAndHashCode.Exclude
    @Getter
    @Transient
    private transient MultipartFile multiPartFile; // Only stored until the content of the file is loaded into the content store.

    /**
     * Base file name.
     */
    @EqualsAndHashCode.Exclude
    @Getter
    @Transient
    private transient String baseFilename; // Only stored until the content of the file is loaded into the content store.

    /**
     * Creates a new document.
     */
    public Document()
    {
        super(EntityType.DOCUMENT);

        setActive();
    }

    /**
     * Creates a new document.
     * @param owner Document owner identity.
     * @param documentType Document type.
     */
    public Document(final @NonNull EntityIdentity owner, final @NonNull DocumentType documentType)
    {
        super(EntityType.DOCUMENT);

        setActive();
        this.documentType = documentType;
        this.owner = owner;
        //owner.addDocument(this);
    }

    /**
     * Creates a new document given its path name.
     * @param owner Document owner identity.
     * @param documentType Document type.
     * @param filename File name.
     * @throws DocumentContentException Thrown in case an error occurred while processing the document content.
     */
    public Document(final @NonNull EntityIdentity owner, final @NonNull DocumentType documentType, final @NonNull String filename) throws DocumentContentException
    {
        this(owner, documentType);

        this.filename = filename;
        setName(FilenameUtils.getName(FilenameUtils.removeExtension(filename)));
        setExtension(FilenameUtils.getExtension(this.filename));

        detectMimeType(filename);
    }

    /**
     * Creates a new document given an associated media file.
     * @param owner Document owner identity.
     * @param documentType Document type.
     * @param file File.
     * @throws DocumentContentException Thrown in case an error occurred while processing the document content.
     */
    public Document(final @NonNull EntityIdentity owner, final @NonNull DocumentType documentType, final @NonNull File file) throws DocumentContentException
    {
        this(owner, documentType);

        this.filename = file.getName();
        setName(FilenameUtils.getName(FilenameUtils.removeExtension(filename)));
        setExtension(FilenameUtils.getExtension(this.filename));

        detectMimeType(file);
    }

    /**
     * Creates a new document given its path name.
     * @param owner Document owner identity.
     * @param documentType Document type.
     * @param multiPartFile Multipart file.
     * @throws DocumentContentException Thrown in case an error occurred while processing the document content.
     */
    public Document(final @NonNull EntityIdentity owner, final @NonNull DocumentType documentType, final @NonNull MultipartFile multiPartFile) throws DocumentContentException
    {
        this(owner, documentType);

        this.filename = multiPartFile.getOriginalFilename();
        this.multiPartFile = multiPartFile;
        setName(FilenameUtils.getName(FilenameUtils.removeExtension(filename)));
        setExtension(FilenameUtils.getExtension(filename));
        detectMimeType(multiPartFile);
    }

    public final void setContent(final @NonNull String filename) throws DocumentContentException
    {
        try
        {
            detectMimeType(filename);
            this.filename = FilenameUtils.getName(filename);
            this.extension = FilenameUtils.getExtension(filename);
            content = new FileInputStream(FileHelper.getFile(filename));
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e);
        };
    }

    public final void setContent(final @NonNull File file) throws DocumentContentException
    {
        try
        {
            detectMimeType(file.getName());
            this.filename = FilenameUtils.getName(file.getName());
            this.extension = FilenameUtils.getExtension(file.getName());
            content = new FileInputStream(FileHelper.getFile(file.getName()));
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e);
        };
    }

    public final void setContent(final InputStream inputStream)
    {
        this.content = inputStream;
    }

    /**
     * Detects the media file {@code Mime} type.
     * @param filename File name.
     * @throws DocumentContentException Thrown in case an error occurred while processing the media file.
     */
    private void detectMimeType(final @NonNull String filename) throws DocumentContentException
    {
        try
        {
            mimeType = new Tika().detect(FileHelper.getFile(filename));
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e.getMessage());
        }
    }

    /**
     * Detects the media file {@code Mime} type.
     * @param file File.
     * @throws DocumentContentException Thrown in case an error occurred while processing the media file.
     */
    private void detectMimeType(final @NonNull File file) throws DocumentContentException
    {
        try
        {
            mimeType = new Tika().detect(file);
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e.getMessage());
        }
    }

    /**
     * Detects the media file {@code Mime} type.
     * @param inputStream Input Stream.
     * @throws DocumentContentException Thrown in case an error occurred while processing the media file.
     */
    private void detectMimeType(final @NonNull InputStream inputStream) throws DocumentContentException
    {
        try
        {
            mimeType = new Tika().detect(inputStream);
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e.getMessage());
        }
    }

    /**
     * Detects the media file {@code Mime} type.
     * @param multiPartFile Multi part file.
     * @throws DocumentContentException Thrown in case an error occurred while processing the media file.
     */
    private void detectMimeType(final @NonNull MultipartFile multiPartFile) throws DocumentContentException
    {
        try
        {
            mimeType = new Tika().detect(multiPartFile.getInputStream());
        }
        catch (Exception e)
        {
            throw new DocumentContentException(e.getMessage());
        }
    }

    /**
     * Returns the document output full path name (path + name).
     * @param outputPath Output path and file name.
     * @return Document full path and file name.
     */
    public final String getOutputFilename(final @NonNull String outputPath)
    {
        String end = getName() + "." + extension;

        return outputPath.endsWith(File.separator)
                ? (outputPath + end)
                : (outputPath + File.separator + end);
    }
}
