package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Question;
import br.com.zup.mercadolivre.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewQuestionRequest {

    @NotBlank
    private String title;

    @NotNull
    private Long productId;

    public NewQuestionRequest(@NotBlank String title, @NotNull Long productId) {
        this.title = title;
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public Long getProductId() {
        return productId;
    }

    public Question toModel(Product product, User owner) {
        return new Question(title, product, owner);
    }
}
