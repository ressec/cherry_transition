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
package com.hemajoo.commerce.cherry.rest.error;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * A global exception handler.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @NotNull
    public static ResponseEntity<String> handleExceptions(Exception exception, WebRequest request)
    {
        return ResponseEntity.badRequest().body(exception.getCause().getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @NotNull
    public static ResponseEntity<String> handleIllegalArgumentExceptions(Exception exception, WebRequest request)
    {
        return ResponseEntity.badRequest().body(exception.getCause().getMessage());
    }

    /**
     * Handles a {@link ConstraintViolationException} exception.
     * @param exception Exception to handle.
     * @param request Web request.
     * @return Response entity containing the errors.
     */
    @ResponseBody
    @NotNull
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @SuppressWarnings("java:S3655")
    public static ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request)
    {
        String message = exception.getMessage();
        String status;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Optional<ConstraintViolation<?>> first = exception.getConstraintViolations().stream().findFirst();

        // Extract the HttpsStatus code
        if (first.isPresent())
        {
            message = first.get().getMessage();
            if (message != null && message.contains("@@"))
            {
                status = message.substring(0, message.indexOf("@@"));
                httpStatus = HttpStatus.valueOf(Integer.parseInt(status.substring(0, 3)));
                message = message.substring(message.indexOf("@@") + 2);
            }
        }

        return new ResponseEntity<>(message, httpStatus);
    }
}
