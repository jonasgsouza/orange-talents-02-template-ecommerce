package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewPurchaseRequest;
import br.com.zup.mercadolivre.exception.InsufficientInventoryException;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import br.com.zup.mercadolivre.service.Emails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class PurchaseController {
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;
    private Emails emails;

    public PurchaseController(ProductRepository productRepository, PurchaseRepository purchaseRepository, Emails emails) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
        this.emails = emails;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> create(@RequestBody @Valid NewPurchaseRequest request,
                                         @AuthenticationPrincipal User buyer,
                                         UriComponentsBuilder uriBuilder) {
        var purchase = request.toModel(productRepository, buyer);
        var decreased = purchase.decreaseProductInventory();
        if (decreased) {
            purchaseRepository.save(purchase);
            var paymentUrl = purchase.generatePaymentUrl(uriBuilder);
            emails.newPurchase(purchase);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(paymentUrl)).build();
        }
        throw new InsufficientInventoryException();
    }
}
