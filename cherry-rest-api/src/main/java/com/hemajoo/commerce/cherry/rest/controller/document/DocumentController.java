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

import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidPersonId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * REST controller providing service endpoints to manage the document entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Api(tags = "Document")
//@ComponentScan(basePackageClasses = { EmailAddressValidatorForUpdate.class, EmailAddressServiceCore.class })
@Validated
@RestController
@RequestMapping("/api/v1/document")
public class DocumentController
{
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
     * @return Document.
     * @throws DocumentException Thrown to indicate an error occurred while trying to create a random document.
     */
    @ApiOperation(value = "Create a new random document")
    @PostMapping("/random")
    public ResponseEntity<String> random(
            @ApiParam(value = "Person identifier (UUID) being the parent of the new document", name = "personId", required = true)
            @Valid @ValidPersonId @NotNull @RequestParam String personId) throws DocumentContentException
    {
        ServerDocumentEntity document = DocumentRandomizer.generateServerEntity(false);

        ServerPersonEntity person = servicePerson.getPersonService().findById(UUID.fromString(personId));
        document.setOwner(person);
        document = servicePerson.getDocumentService().save(document);

        return ResponseEntity.ok(String.format("Successfully saved document with id: '%s', with content id: '%s'", document.getId(), document.getContentId()));
    }
}
