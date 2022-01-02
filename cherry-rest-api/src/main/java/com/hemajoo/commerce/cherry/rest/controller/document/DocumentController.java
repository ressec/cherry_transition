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
package com.hemajoo.commerce.cherry.rest.controller.document;

import com.hemajoo.commerce.cherry.commons.type.StatusType;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.PersonCheckId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * REST controller providing service endpoints to manage the document entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Api(tags = "Document")
@Validated
@RestController
@RequestMapping("/api/v1/document")
public class DocumentController
{
    private static final Tika TIKA = new Tika();

    /**
     * Document service.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    /**
     * Service to count the number of documents.
     * @return Number of documents.
     */
    @ApiOperation(value = "Count the total number of documents")
    @GetMapping("/count")
    public long count()
    {
        return servicePerson.getDocumentService().count();
    }

    /**
     * Service to create a random document.
     * @param personId Person identifier being the parent of the document to create.
     * @return Message.
     * @throws DocumentException Thrown to indicate an error occurred while trying to create a random document.
     */
    @ApiOperation(value = "Create a new random document")
    @PostMapping("/random")
    public ResponseEntity<String> random(
            @ApiParam(value = "Person identifier (UUID) being the parent of the new document", name = "personId", required = true)
            @Valid @PersonCheckId @NotNull @RequestParam String personId) throws DocumentContentException
    {
        ServerDocumentEntity document = DocumentRandomizer.generateServerEntity(false);

        ServerPersonEntity person = servicePerson.getPersonService().findById(UUID.fromString(personId));
        document.setOwner(person);
        document = servicePerson.getDocumentService().save(document);

        return ResponseEntity.ok(String.format("Successfully saved document with id: '%s', with content id: '%s'", document.getId(), document.getContentId()));
    }

    /**
     * Service to upload a file for a person.
     * @param file File to upload.
     * @param name Name for the file (if blank, filename will be used).
     * @param description Description of the file (informative) and not mandatory.
     * @param reference Reference (internal) and not mandatory.
     * @param tags Tags of the file (comma separated list of tags) and not mandatory.
     * @param personId Person identifier being the owner of the uploaded document.
     * @return Message of the result of the upload.
     */
    @ApiOperation(value = "Upload a document")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file,
                                         @ApiParam(value = "Name", name = "name", required = false)
                                         @RequestParam String name,
                                         @ApiParam(value = "Description", name = "description", required = false)
                                         @RequestParam String description,
                                         @ApiParam(value = "Reference", name = "reference", required = false)
                                         @RequestParam String reference,
                                         @ApiParam(value = "Tags", name = "tags", required = false)
                                         @RequestParam String tags,
                                         @ApiParam(value = "Person identifier (UUID) being the parent of the new document", name = "personId", required = true)
                                         @Valid @PersonCheckId @NotNull @RequestParam String personId)
    {
        ServerDocumentEntity document;

        if (file.isEmpty())
        {
            return new ResponseEntity<>("You must select a file!", HttpStatus.BAD_REQUEST);
        }

        try
        {
            document = uploadDocument(file);

            ServerPersonEntity person = servicePerson.getPersonService().findById(UUID.fromString(personId));
            document.setName(name != null ? name : file.getName());
            document.setDescription(description);
            document.setReference(reference);
            document.setTags(tags);
            document.setStatusType(StatusType.ACTIVE);
            document.setOwner(person);
            servicePerson.getDocumentService().save(document);
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        String message = String.format("Successfully uploaded file: '%s', with id: '%s', with content id: '%s'", file.getOriginalFilename(), document.getId(), document.getContentId());
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
    }

    private ServerDocumentEntity uploadDocument(MultipartFile file) throws IOException
    {
        ServerDocumentEntity document = new ServerDocumentEntity();

        InputStream stream = new ByteArrayInputStream(file.getBytes());

        document.setContent(stream);
        document.setContentLength(file.getBytes().length);
        document.setFilename(file.getOriginalFilename());
        document.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        document.setMimeType(TIKA.detect(stream));

        stream.close();

        return document;
    }
}
