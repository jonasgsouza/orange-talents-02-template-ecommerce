package br.com.zup.mercadolivre.util;

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
}
