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
package com.hemajoo.commerce.cherry.rest.controller.person;

import com.hemajoo.commerce.cherry.persistence.base.factory.ServerEntityFactory;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressServiceCore;
import com.hemajoo.commerce.cherry.persistence.person.validation.engine.EmailAddressValidationEngine;
import com.hemajoo.commerce.cherry.persistence.person.validation.validator.EmailAddressValidatorForUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A {@code REST API} controller providing endpoints to manage email addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Api(tags = "Email Addresses")
@ComponentScan(basePackageClasses = { EmailAddressValidatorForUpdate.class, EmailAddressServiceCore.class })
//@GroupSequence( { EmailAddressController.class, BasicValidation.class, ExtendedValidation.class } )
@Validated
@RestController
@RequestMapping("/api/v1/person/email")
public class EmailAddressController
{
    /**
     * Server entity factory.
     */
    @Autowired
    private ServerEntityFactory entityFactory;

    /**
     * Email address validation engine.
     */
    @Autowired
    private EmailAddressValidationEngine emailAddressRuleEngine;

    /**
     * Returns the total number of email addresses.
     * @return Number of email addresses.
     */
    @ApiOperation(value = "Count the total number of email addresses."/*, notes = "Count the total number of email addresses."*/)
    @GetMapping("/count")
    public long count()
    {
        return entityFactory.getEmailAddressService().count();
    }

//    /**
//     * Retrieves an email address given its unique identifier.
//     * @param id Email address identifier.
//     * @return Email address matching the given identifier.
//     */
//    @ApiOperation(value = "Retrieve an email address given its identifier."/*, notes = "Retrieve an email address given its identifier."*/)
//    @GetMapping("/get/{id}")
//    public ResponseEntity<ClientEmailAddressEntity> get(
//            @ApiParam(value = "Email address identifier", required = true)
//            @Valid @ValidEmailAddressId // Handles email id validation automatically, need both annotations!
//            @NotNull
//            @PathVariable String id)
//    {
//        ServerEmailAddressEntity serverEmailAddress = entityFactory.getEmailAddressService().findById(UUID.fromString(id));
//        return ResponseEntity.ok(EmailAddressConverter.convertServer(serverEmailAddress));
//    }
//
//    /**
//     * Endpoint service to add a new email address.
//     * @param emailAddress Email address to add.
//     * @return Response.
//     * @throws EmailAddressException Thrown in case an error occurred while trying to save the email address server entity.
//     */
//    @ApiOperation(value = "Create a new email address."/*,
//            notes = "Notes: <i>No need to set the postal address identifier (id) as it is automatically generated. If set, it will be ignored!</i>"*/)
//    @PostMapping("/create")
//    public ResponseEntity<ClientEmailAddressEntity> create(
//            @ApiParam(value = "Email address", required = true)
//            @Valid @ValidEmailAddressForCreation @RequestBody ClientEmailAddressEntity emailAddress) throws EmailAddressException
//    {
//        ServerEmailAddressEntity serverEmailAddress = EmailAddressConverter.convertClient(emailAddress, entityFactory);
//        entityFactory.getEmailAddressService().save(serverEmailAddress);
//
//        return ResponseEntity.ok(EmailAddressConverter.convertServer(serverEmailAddress));
//    }
//
//    /**
//     * Endpoint service to create a random email address for a given person identifier.
//     * @param personId Person identifier.
//     * @return Response entity containing the randomly generated {@link ClientEmailAddressEntity}.
//     * @throws EmailAddressException Thrown in case an error occurred while trying to create an email address server entity.
//     */
//    @ApiOperation(value = "Create a new random email address for the given person identifier.")
//    @PostMapping("/random")
//    public ResponseEntity<ClientEmailAddressEntity> random(
//            @ApiParam(value = "Person identifier (UUID) being the owner of the email address to create.", name = "personId", required = true)
//            @Valid @ValidPersonId @NotNull @RequestParam String personId) throws EmailAddressException
//    {
//        ServerEmailAddressEntity serverEmail = EmailAddressRandomizer.generateServer(false);
//
//        ServerPersonEntity person = entityFactory.getPersonService().findById(UUID.fromString(personId));
//        serverEmail.setPerson(person);
//        entityFactory.getEmailAddressService().save(serverEmail);
//
//        return ResponseEntity.ok(EmailAddressConverter.convertServer(serverEmail));
//    }
//
//    /**
//     * Endpoint service to update an email address.
//     * @param email Email address to update.
//     * @return Response.
//     */
//    @ApiOperation(value = "Update the given email address."/*, notes = "Update an email address given the new values."*/)
//    @PutMapping("/update")
//    //@Transactional
//    public ResponseEntity<ClientEmailAddressEntity> update(
//            @NonNull /*@Valid @ValidEmailAddressForUpdate*/ @RequestBody ClientEmailAddressEntity email) throws EntityException
//    {
//        //emailAddressRuleEngine.validateEmailAddressId(email);
//        emailAddressRuleEngine.validateEmailForUpdate(email);
//
//        ServerEmailAddressEntity source = EmailAddressConverter.convertClient(email, entityFactory);
//        ServerEmailAddressEntity result = entityFactory.getEmailAddressService().update(source);
//        ClientEmailAddressEntity client = EmailAddressConverter.convertServer(result);
//
//        return ResponseEntity.ok(client);
//    }
//
//    /**
//     * Endpoint service to delete an email address.
//     * @param id Email address identifier.
//     * @return Response.
//     */
//    @ApiOperation(value = "Delete an email address.", notes = "Delete an email address given its identifier.")
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> delete(
//            @ApiParam(value = "Email address identifier", required = true)
//            @NotNull @Valid @ValidEmailAddressId @PathVariable String id)
//    {
//        entityFactory.getEmailAddressService().deleteById(UUID.fromString(id));
//
//        return ResponseEntity.ok(String.format("Email address id: '%s' has been deleted successfully!", id));
//    }
//
//    /**
//     * Searches for email addresses given criteria.
//     * @param search Email address search object.
//     * @return List of email identifiers matching the given criteria if some have been found, an empty list otherwise.
//     */
//    @ApiOperation(value = "Search for email addresses.", notes = "Search for email addresses matching the given predicates. Fill only the fields you want to be taken into account for the search.")
//    @GetMapping("/search")
//    public ResponseEntity<List<String>> search(final @RequestBody @NonNull SearchEmailAddress search)
//    {
//        List<ClientEmailAddressEntity> entities = EmailAddressConverter.convertServerList(entityFactory.getEmailAddressService().search(search));
//
//        return ResponseEntity.ok(GenericEntityConverter.toIdList(entities));
//    }
//
//    /**
//     * Endpoint service to query email addresses given criteria.
//     * @param search Email address search object.
//     * @return List of client email addresses if some have been found, an empty list otherwise.
//     */
//    @ApiOperation(value = "Query email addresses", notes = "Returns a list of email addresses matching the given criteria.")
//    @GetMapping("/query")
//    public ResponseEntity<List<ClientEmailAddressEntity>> query(final @RequestBody @NonNull SearchEmailAddress search)
//    {
//        return ResponseEntity.ok(EmailAddressConverter.convertServerList(entityFactory.getEmailAddressService().search(search)));
//    }
}
