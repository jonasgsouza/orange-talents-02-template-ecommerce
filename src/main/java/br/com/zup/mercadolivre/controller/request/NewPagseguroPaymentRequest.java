package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewPagseguroPaymentRequest implements NewPaymentRequest {
    @NotBlank
    private String paymentId;
    @NotNull
    private PagseguroPaymentStatus status;

    public NewPagseguroPaymentRequest(String paymentId, PagseguroPaymentStatus status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public PagseguroPaymentStatus getStatus() {
        return status;
    }

    public Transaction toModel(Purchase purchase) {
        return new Transaction(paymentId, purchase, status.toLocalStatus());
    }
}
