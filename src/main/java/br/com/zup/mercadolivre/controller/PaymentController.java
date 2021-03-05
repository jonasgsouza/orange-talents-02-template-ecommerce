package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewPagseguroPaymentRequest;
import br.com.zup.mercadolivre.controller.request.NewPaymentRequest;
import br.com.zup.mercadolivre.controller.request.NewPaypalPaymentRequest;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import br.com.zup.mercadolivre.service.PaymentEventDispatcher;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PurchaseRepository purchaseRepository;
    private final PaymentEventDispatcher eventDispatcher;

    public PaymentController(PurchaseRepository purchaseRepository, PaymentEventDispatcher eventDispatcher) {
        this.purchaseRepository = purchaseRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @PostMapping("/pagseguro/{purchaseUuid}")
    @Transactional
    public void pagseguroReturn(@RequestBody NewPagseguroPaymentRequest request,
                                @PathVariable UUID purchaseUuid) {
        processPayment(purchaseUuid, request);
    }

    @PostMapping("/paypal/{purchaseUuid}")
    @Transactional
    public void paypalReturn(@RequestBody NewPaypalPaymentRequest request,
                             @PathVariable UUID purchaseUuid) {
        processPayment(purchaseUuid, request);
    }

    private void processPayment(UUID purchaseUuid, NewPaymentRequest request) {
        var purchase = purchaseRepository.findByUuid(purchaseUuid).orElseThrow(() -> new NotFoundException(purchaseUuid));
        var transaction = request.toModel(purchase);
        purchase.addTransaction(transaction);
        eventDispatcher.dispatch(purchase);
    }


}
