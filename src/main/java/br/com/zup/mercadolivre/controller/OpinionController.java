package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewOpinionRequest;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.OpinionRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class OpinionController {

    private final OpinionRepository opinionRepository;
    private final ProductRepository productRepository;

    public OpinionController(OpinionRepository opinionRepository, ProductRepository productRepository) {
        this.opinionRepository = opinionRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/products/{id}/opinions")
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewOpinionRequest request, @PathVariable Long id, @AuthenticationPrincipal User owner) {
        var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        opinionRepository.save(request.toModel(product, owner));
        return ResponseEntity.ok().build();
    }
}
