package br.com.mvp.cadastroclienteapi.service.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.ConstraintViolation;

@Getter
@Setter
@AllArgsConstructor
public class ObjectError {

    private final String message;
    private final String field;
    private final Object parameter;

    public static ObjectError fromConstraintViolation(final ConstraintViolation<?> violation) {
        return new ObjectError(violation.getMessage(), violation.getPropertyPath().toString(), violation.getInvalidValue());
    }
}
