package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PaymentEventDispatcher {

    private final Set<PaymentEventListener> listeners;

    public PaymentEventDispatcher(Set<PaymentEventListener> listeners) {
        this.listeners = listeners;
    }

    public void dispatch(Purchase purchase) {
        listeners.forEach(listener -> {
            if (purchase.isFinished()) {
                listener.succeeded(purchase);
            } else {
                listener.failed(purchase);
            }
        });
    }
}
