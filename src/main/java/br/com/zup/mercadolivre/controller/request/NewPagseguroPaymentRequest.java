package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;

import java.util.UUID;

public class NewPagseguroPaymentRequest {
    private String paymentId;
    private UUID purchaseId;
    private PagseguroPaymentStatus status;

    public NewPagseguroPaymentRequest(String paymentId, UUID purchaseId, PagseguroPaymentStatus status) {
        this.paymentId = paymentId;
        this.purchaseId = purchaseId;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public UUID getPurchaseId() {
        return purchaseId;
    }

    public PagseguroPaymentStatus getStatus() {
        return status;
    }

    public Transaction toModel(Purchase purchase) {
        return new Transaction(paymentId, purchase, status.toLocalStatus());
    }
}
