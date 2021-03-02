package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;

public interface NewPaymentRequest {

    public Transaction toModel(Purchase purchase);
}
