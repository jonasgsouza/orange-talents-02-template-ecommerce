package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.TransactionStatus;

public enum PagseguroPaymentStatus {
    SUCESSO(TransactionStatus.SUCCEEDED), ERRO(TransactionStatus.FAILED);
    private TransactionStatus localStatus;

    PagseguroPaymentStatus(TransactionStatus localStatus) {
        this.localStatus = localStatus;
    }

    public TransactionStatus toLocalStatus() {
        return localStatus;
    }
}
