package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Question;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<QuestionResponse> mapList(List<Question> questions) {
        return questions.stream().map(QuestionResponse::new).collect(Collectors.toList());
    }
}
