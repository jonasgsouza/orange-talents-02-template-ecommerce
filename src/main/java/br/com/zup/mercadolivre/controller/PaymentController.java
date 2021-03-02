package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewPagseguroPaymentRequest;
import br.com.zup.mercadolivre.controller.request.NewPaypalPaymentRequest;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Transaction;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import br.com.zup.mercadolivre.util.Emails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PurchaseRepository purchaseRepository;
    private final Emails emails;

    public PaymentController(PurchaseRepository purchaseRepository, Emails emails) {
        this.purchaseRepository = purchaseRepository;
        this.emails = emails;
    }

    @PostMapping("/pagseguro/{purchaseUuid}")
    @Transactional
    public void pagseguroReturn(@RequestBody NewPagseguroPaymentRequest request,
                                @PathVariable UUID purchaseUuid,
                                UriComponentsBuilder uriBuilder) {
        var purchase = purchaseRepository.findByUuid(purchaseUuid).orElseThrow(() -> new NotFoundException(purchaseUuid));
        var transaction = request.toModel(purchase);
        processPayment(purchase, transaction, uriBuilder);
    }

    @PostMapping("/paypal/{purchaseUuid}")
    @Transactional
    public void paypalReturn(@RequestBody NewPaypalPaymentRequest request,
                                @PathVariable UUID purchaseUuid,
                                UriComponentsBuilder uriBuilder) {
        var purchase = purchaseRepository.findByUuid(purchaseUuid).orElseThrow(() -> new NotFoundException(purchaseUuid));
        var transaction = request.toModel(purchase);
        processPayment(purchase, transaction, uriBuilder);
    }

    private void processPayment(Purchase purchase, Transaction transaction, UriComponentsBuilder uriBuilder) {
        purchase.addTransaction(transaction);
        if (transaction.succeeded()) {
            //Nota fiscal
            //ranking
            purchaseRepository.save(purchase);
            emails.purchasePaid(purchase);
        } else {
            emails.purchaseFailed(purchase, purchase.generatePaymentUrl(uriBuilder));
        }
    }


}
