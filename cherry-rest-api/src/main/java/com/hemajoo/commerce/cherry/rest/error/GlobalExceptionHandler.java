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

import com.hemajoo.commerce.cherry.commons.exception.AbstractEntityCheckedException;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * A global exception handler for the {@code Cherry} REST API controllers.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @NotNull
    public final ResponseEntity<RestApiResponse> handleAllExceptions(Exception exception, WebRequest request)
    {
        return RestApiResponse.ko(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()).getEntity();
    }

    /**
     * Handles a {@link ConstraintViolationException} exception.
     * @param exception Exception to handle.
     * @param request Web request.
     * @return Response entity containing the errors.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @NotNull
    public final ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request)
    {
        ErrorResponse response = ErrorResponse.ko(HttpStatus.INTERNAL_SERVER_ERROR);

        for (ConstraintViolation<?> violation : exception.getConstraintViolations())
        {
            response.addError(violation.getMessage());
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles {@link AbstractEntityCheckedException} exceptions.
     * @param exception Exception to handle.
     * @param request Web request.
     * @return Response entity containing the exception errors.
     */
    @ExceptionHandler( { AbstractEntityCheckedException.class } )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @NotNull
    public final ResponseEntity<RestApiResponse> handleEMailAddressException(AbstractEntityCheckedException exception, WebRequest request)
    {
        RestApiResponse response = RestApiResponse.ko(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return response.getEntity();
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request)
    {
        ErrorResponse response = ErrorResponse.ko(status);

        for (ObjectError error : exception.getBindingResult().getAllErrors())
        {
            response.addError(Objects.requireNonNull(error.getDefaultMessage()));
        }

        return new ResponseEntity<>(response, response.getStatus());
    }
}
