package br.com.zup.mercadolivre.util;

import javax.validation.constraints.NotBlank;

public interface Mailer {

    void send(@NotBlank String from, @NotBlank String to, @NotBlank String subject, @NotBlank String content);
}
