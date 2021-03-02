package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.web.util.UriComponentsBuilder;

public class PagSeguro implements IPaymentGateway {

    @Override
    public String generateUrl(Purchase purchase, UriComponentsBuilder uriBuilder) {
        var redirectUrl = uriBuilder
                .path("/payments/pagseguro/{id}")
                .buildAndExpand(purchase.getUuid())
                .toUri();
        return "pagseguro.com?returnId=" + purchase.getUuid() + "&redirectUrl=" + redirectUrl;
    }
}
