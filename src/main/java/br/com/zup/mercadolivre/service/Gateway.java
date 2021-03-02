package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.web.util.UriComponentsBuilder;

public interface Gateway {

    String generateUrl(Purchase order, UriComponentsBuilder uriBuilder);
}
