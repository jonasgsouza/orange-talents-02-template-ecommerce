package br.com.zup.mercadolivre.controller.request;

import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.PaymentGateway;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.validation.annotation.Exists;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewPurchaseRequest {

    @NotNull
    private PaymentGateway paymentGateway;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Exists(field = "id", modelClass = Product.class)
    private Long productId;

    public NewPurchaseRequest(@NotNull PaymentGateway paymentGateway, @NotNull Integer quantity, @NotNull Long productId) {
        this.paymentGateway = paymentGateway;
        this.quantity = quantity;
        this.productId = productId;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Purchase toModel(ProductRepository productRepository, User buyer) {
        var product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));
        return new Purchase(quantity, paymentGateway, product, buyer);
    }
}
