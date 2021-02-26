package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Question;

public class QuestionResponse {
    private String title;

    @Deprecated
    public QuestionResponse() {
    }

    public QuestionResponse(Question question) {
        this.title = question.getTitle();
    }

    public String getTitle() {
        return title;
    }
}
