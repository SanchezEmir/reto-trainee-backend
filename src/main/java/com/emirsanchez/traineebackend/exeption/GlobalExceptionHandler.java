package com.emirsanchez.traineebackend.exeption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.emirsanchez.traineebackend.exeption.BadRequestException;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleValidationError(MethodArgumentNotValidException manve) {
        HashMap errorDetail = getErrorDetail(manve.getBindingResult());

        return new ResponseEntity(errorDetail, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BindException.class)
    ResponseEntity<?> handleBindException(BindException ex) {
        HashMap errorDetail = getErrorDetail(ex);

        return new ResponseEntity(errorDetail, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private HashMap getErrorDetail(BindingResult bindingResult) {
        HashMap errorDetail = new HashMap<String, Object>();

        errorDetail.put("code", "invalid_form");
        errorDetail.put("title", "Error de validación");
        errorDetail.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());

        HashSet errors = new HashSet<String>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (FieldError fe : fieldErrors) {
            String message = messageSource.getMessage(fe, Locale.getDefault());
            errors.add(message);
        }
        errorDetail.put("errors", errors);

        return errorDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    void handleEntityNotFoundException(EntityNotFoundException ex) {
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        HashMap errorDetail = new HashMap<String, Object>();

        errorDetail.put("code", "bad_request");
        errorDetail.put("title", "Error en la solicitud");
        errorDetail.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetail.put("message", ex.getMessage());

        return new ResponseEntity(errorDetail, HttpStatus.BAD_REQUEST);
    }

//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(ForbiddenException.class)
//    void handleForbiddenException(ForbiddenException ex) {
//    }

}
