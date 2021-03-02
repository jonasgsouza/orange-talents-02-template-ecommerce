package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.service.Gateway;
import br.com.zup.mercadolivre.service.PagSeguro;
import br.com.zup.mercadolivre.service.PayPal;

public enum PaymentGateway {
    PAYPAL(new PayPal()), PAGSEGURO(new PagSeguro());

    private Gateway gateway;

    PaymentGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public Gateway getGateway() {
        return gateway;
    }
}
