package br.com.zup.mercadolivre.util;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.web.util.UriComponentsBuilder;

public class PayPal implements IPaymentGateway {

    @Override
    public String generateUrl(Purchase purchase, UriComponentsBuilder uriBuilder) {
        var redirectUrl = uriBuilder
                .path("/payments/paypal")
                .buildAndExpand()
                .toUri();
        return "paypal.com/" + purchase.getUuid() + "?redirectUrl=" + redirectUrl;
    }
}
