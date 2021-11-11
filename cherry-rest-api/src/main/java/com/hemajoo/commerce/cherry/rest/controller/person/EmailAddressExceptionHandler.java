/*
 * (C) Copyright Hemajoo Systems Inc. 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.rest.controller.person;

import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Email address exception handler.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@RestControllerAdvice
public class EmailAddressExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(EmailAddressException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @NotNull
    public static ResponseEntity<String> handleEmailAddressExceptions(EmailAddressException exception, WebRequest request)
    {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
    }
}
