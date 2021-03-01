package br.com.zup.mercadolivre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientInventoryException extends RuntimeException {

    public InsufficientInventoryException() {
        super("Não existe estoque suficiente para realizar a compra");
    }
}
