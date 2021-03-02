package br.com.zup.mercadolivre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private Object id;

    public NotFoundException(Object id) {
        super("Resource with id " + id + " not found");
    }
}
