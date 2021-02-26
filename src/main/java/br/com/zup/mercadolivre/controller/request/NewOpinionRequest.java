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
    private Short note;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;


    public NewOpinionRequest(@Min(1) @Max(5) Short note, @NotBlank String title, @NotBlank @Size(max = 500) String description) {
        this.note = note;
        this.title = title;
        this.description = description;
    }

    public Short getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Opinion toModel(Product product, User owner) {
        return new Opinion(note, title, description, product, owner);
    }
}
