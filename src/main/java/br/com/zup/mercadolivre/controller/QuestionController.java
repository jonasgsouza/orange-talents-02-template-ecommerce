package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewQuestionRequest;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.QuestionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final ProductRepository productRepository;

    public QuestionController(QuestionRepository questionRepository, ProductRepository productRepository) {
        this.questionRepository = questionRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewQuestionRequest request, @AuthenticationPrincipal User owner) {
        questionRepository.save(request.toModel(productRepository, owner));
        return ResponseEntity.ok().build();
    }
}
