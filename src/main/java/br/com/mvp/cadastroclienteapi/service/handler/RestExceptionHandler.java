package br.com.mvp.cadastroclienteapi.service.handler;


import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> errors = getErrors(ex);
        ErrorResponse errorResponse = getErrorResponse(ex, status, errors);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), status.value(), status.getReasonPhrase(), null, Collections.emptyList()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(e.getMessage(), NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), null, null));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        List<ObjectError> errors = e.getConstraintViolations().stream().map(ObjectError::fromConstraintViolation).collect(toList());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), null, errors);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(ExceptionCliente.class)
    ResponseEntity<ErrorResponse> handleExceptionCliente(ExceptionCliente e) {
    	ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), UNPROCESSABLE_ENTITY.value(), UNPROCESSABLE_ENTITY.getReasonPhrase(), null, null);
    	return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    private ErrorResponse getErrorResponse(MethodArgumentNotValidException ex, HttpStatus status, List<ObjectError> errors) {
        return new ErrorResponse("Campos inválidos", status.value(), status.getReasonPhrase(), ex.getBindingResult().getObjectName(), errors);
    }

    private List<ObjectError> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream().map(e -> new ObjectError(e.getDefaultMessage(), e.getField(), e.getRejectedValue())).collect(toList());
    }

}
