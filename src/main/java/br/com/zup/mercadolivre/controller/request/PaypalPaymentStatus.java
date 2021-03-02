package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaypalPaymentStatus {
    SUCESSO(1, TransactionStatus.SUCCEEDED), ERRO(0, TransactionStatus.FAILED);

    private Integer intVal;
    private TransactionStatus localStatus;

    PaypalPaymentStatus(Integer intVal, TransactionStatus localStatus) {
        this.intVal = intVal;
        this.localStatus = localStatus;
    }

    public TransactionStatus toLocalStatus() {
        return localStatus;
    }

    @JsonValue
    public Integer getIntVal() {
        return intVal;
    }
}
