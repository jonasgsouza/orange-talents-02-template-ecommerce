package br.com.zup.mercadolivre.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
@Profile({"default", "test"})
public class DevMailer implements Mailer {

    @Override
    public void send(@NotBlank String from, @NotBlank String to, @NotBlank String subject, @NotBlank String content) {
        System.out.println("===============================================================");
        System.out.println("Email from: " + from);
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Content: " + content);
        System.out.println("===============================================================");
    }
}
