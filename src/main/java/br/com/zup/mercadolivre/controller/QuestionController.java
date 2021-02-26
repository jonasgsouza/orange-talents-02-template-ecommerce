package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewQuestionRequest;
import br.com.zup.mercadolivre.controller.response.QuestionResponse;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.QuestionRepository;
import br.com.zup.mercadolivre.util.Emails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final ProductRepository productRepository;
    private final Emails emails;

    public QuestionController(QuestionRepository questionRepository, ProductRepository productRepository, Emails emails) {
        this.questionRepository = questionRepository;
        this.productRepository = productRepository;
        this.emails = emails;
    }

    @PostMapping("/products/{id}/questions")
    @Transactional
    public ResponseEntity<List<QuestionResponse>> create(@RequestBody @Valid NewQuestionRequest request, @PathVariable Long id, @AuthenticationPrincipal User owner) {
        var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        var question = questionRepository.save(request.toModel(product, owner));
        var questions = questionRepository
                .findByProductId(id)
                .stream().map(QuestionResponse::new).collect(Collectors.toList());
        emails.newQuestion(question);
        return ResponseEntity.ok(questions);
    }
}
