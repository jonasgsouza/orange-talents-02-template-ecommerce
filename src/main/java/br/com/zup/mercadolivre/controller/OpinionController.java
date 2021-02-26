package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewOpinionRequest;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.OpinionRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/opinions")
public class OpinionController {

    private final OpinionRepository opinionRepository;
    private final ProductRepository productRepository;

    public OpinionController(OpinionRepository opinionRepository, ProductRepository productRepository) {
        this.opinionRepository = opinionRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewOpinionRequest request, @AuthenticationPrincipal User owner) {
        opinionRepository.save(request.toModel(productRepository, owner));
        return ResponseEntity.ok().build();
    }
}
