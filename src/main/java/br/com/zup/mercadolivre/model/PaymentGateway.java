package br.com.zup.mercadolivre.model;

import br.com.zup.mercadolivre.util.IPaymentGateway;
import br.com.zup.mercadolivre.util.PagSeguro;
import br.com.zup.mercadolivre.util.PayPal;

public enum PaymentGateway {
    PAYPAL(new PayPal()), PAGSEGURO(new PagSeguro());

    private IPaymentGateway gateway;

    PaymentGateway(IPaymentGateway gateway) {
        this.gateway = gateway;
    }

    public IPaymentGateway getGateway() {
        return gateway;
    }
}
