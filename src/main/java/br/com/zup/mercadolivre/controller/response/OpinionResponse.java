package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Opinion;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<OpinionResponse> mapList(List<Opinion> opinions) {
        return opinions.stream().map(OpinionResponse::new).collect(Collectors.toList());
    }
}
