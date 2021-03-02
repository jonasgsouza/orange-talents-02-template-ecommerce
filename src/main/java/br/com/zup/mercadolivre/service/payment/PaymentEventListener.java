package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;

public interface PaymentEventListener {

    default void succeeded(Purchase purchase) {
    }

    default void failed(Purchase purchase) {
    }
}
