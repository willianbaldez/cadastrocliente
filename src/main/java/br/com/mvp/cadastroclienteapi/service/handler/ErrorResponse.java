package br.com.mvp.cadastroclienteapi.service.handler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private final String message;

    private final int code;

    private final String status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String objectName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ObjectError> errors;
}
