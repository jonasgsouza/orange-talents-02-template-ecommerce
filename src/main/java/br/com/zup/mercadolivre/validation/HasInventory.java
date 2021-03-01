package br.com.zup.mercadolivre.validation;

import br.com.zup.mercadolivre.controller.request.NewPurchaseRequest;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.validation.interfaces.LessOrEqualSupplier;
import org.springframework.stereotype.Component;

@Component
public class HasInventory implements LessOrEqualSupplier<NewPurchaseRequest, Integer> {

    private final ProductRepository productRepository;

    public HasInventory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Comparable<Integer> get(NewPurchaseRequest o) {
        var product = productRepository.findById(o.getProductId()).orElseThrow(() -> new NotFoundException(o.getProductId()));
        return product.getInventory();
    }

    @Override
    public String message() {
        return "Não existe estoque suficiente para realizar a compra";
    }
}
