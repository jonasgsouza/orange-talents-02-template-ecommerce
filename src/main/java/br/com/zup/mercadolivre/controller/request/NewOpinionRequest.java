package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.model.Opinion;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewOpinionRequest {

    @Min(1)
    @Max(5)
    private Short rate;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;


    public NewOpinionRequest(@Min(1) @Max(5) Short rate, @NotBlank String title, @NotBlank @Size(max = 500) String description) {
        this.rate = rate;
        this.title = title;
        this.description = description;
    }

    public Short getRate() {
        return rate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Opinion toModel(Product product, User owner) {
        return new Opinion(rate, title, description, product, owner);
    }
}
