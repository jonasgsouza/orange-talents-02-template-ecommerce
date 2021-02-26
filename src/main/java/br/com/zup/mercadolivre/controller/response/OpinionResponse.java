package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Opinion;

public class OpinionResponse {

    private Short note;
    private String title;
    private String description;

    public OpinionResponse(Opinion opinion) {
        this.note = opinion.getRate();
        this.title = opinion.getTitle();
        this.description = opinion.getDescription();
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
}
