package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.Question;
import org.springframework.stereotype.Service;

@Service
public class Emails {

    private Mailer mailer;

    public Emails(Mailer mailer) {
        this.mailer = mailer;
    }

    public void newQuestion(Question question) {
        mailer.send("novapergunta@mercadolivre.com", question.getProductOwner().getEmail(),
                "Nova pergunta de " + question.getOwner().getEmail(), question.getTitle());
    }

    public void newPurchase(Purchase purchase) {
        mailer.send("compras@mercadolivre.com", purchase.getProductOwner().getEmail(),
                "Nova compra de " + purchase.getBuyer().getEmail(), "Produto: " + purchase.getProduct().getName()
                        + ". Id: " + purchase.getUuid());
    }

    public void purchasePaid(Purchase purchase) {
        mailer.send("compras@mercadolivre.com", purchase.getProductOwner().getEmail(),
                "Compra de " + purchase.getBuyer().getEmail() + " foi paga.", purchase.getProduct().getName());
    }

    public void purchaseFailed(Purchase purchase, String paymentUrl) {
        mailer.send("compras@mercadolivre.com", purchase.getBuyer().getEmail(),
                "Falha no processamento da compra ", "Sua compra do produto "
                        + purchase.getProduct().getName() + " falhou. Tente novamente através do link: "
                        + paymentUrl);
    }
}
