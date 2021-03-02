package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewPagseguroPaymentRequest;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import br.com.zup.mercadolivre.util.Emails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PurchaseRepository purchaseRepository;
    private final Emails emails;

    public PaymentController(PurchaseRepository purchaseRepository, Emails emails) {
        this.purchaseRepository = purchaseRepository;
        this.emails = emails;
    }

    @PostMapping("/pagseguro")
    @Transactional
    public void pagseguroReturn(@RequestBody NewPagseguroPaymentRequest request,
                                UriComponentsBuilder uriBuilder) {
        var purchase = purchaseRepository.findByUuid(request.getPurchaseId()).orElseThrow(() -> new NotFoundException(request.getPurchaseId()));
        var transaction = request.toModel(purchase);
        purchase.addTransaction(transaction);
        if (transaction.succeeded()) {
            //Nota fiscal
            //ranking
            emails.purchasePaid(purchase);
            purchaseRepository.save(purchase);
        } else {
            emails.purchaseFailed(purchase, purchase.generatePaymentUrl(uriBuilder));
        }
    }


}
