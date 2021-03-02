package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SellerRankingPaymentEventListener implements PaymentEventListener {

    @Override
    public void succeeded(Purchase purchase) {
        Assert.isTrue(purchase.isFinished(), "Compra não finalizada");
        System.out.println("Chamando endpoint de ranking dos vendedores...");
    }
}
