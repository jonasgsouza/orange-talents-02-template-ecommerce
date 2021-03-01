package br.com.zup.mercadolivre.util;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.web.util.UriComponentsBuilder;

public interface IPaymentGateway {

    String generateUrl(Purchase order, UriComponentsBuilder uriBuilder);
}
