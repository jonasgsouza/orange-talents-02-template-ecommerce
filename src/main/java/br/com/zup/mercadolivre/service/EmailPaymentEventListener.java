package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class EmailPaymentEventListener implements PaymentEventListener {

    private final Emails emails;

    public EmailPaymentEventListener(Emails emails) {
        this.emails = emails;
    }

    @Override
    public void succeeded(Purchase purchase) {
        emails.purchasePaid(purchase);
    }

    @Override
    public void failed(Purchase purchase) {
        emails.purchaseFailed(purchase, purchase.generatePaymentUrl(ServletUriComponentsBuilder.fromCurrentContextPath()));
    }
}
