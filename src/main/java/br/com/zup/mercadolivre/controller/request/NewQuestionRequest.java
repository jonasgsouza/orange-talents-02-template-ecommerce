package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Question;
import br.com.zup.mercadolivre.model.User;

import javax.validation.constraints.NotBlank;

public class NewQuestionRequest {

    @NotBlank
    private String title;

    @Deprecated
    public NewQuestionRequest() {
    }

    public NewQuestionRequest(@NotBlank String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Question toModel(Product product, User owner) {
        return new Question(title, product, owner);
    }
}
