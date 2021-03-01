package br.com.zup.mercadolivre.model;

import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "compras")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid = UUID.randomUUID();

    @Column(name = "quantidade")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.INITIATED;

    @Enumerated(EnumType.STRING)
    @Column(name = "gateway_pagamento")
    private PaymentGateway paymentGateway;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private User buyer;

    @Deprecated
    public Purchase() {
    }

    public Purchase(Integer quantity, PaymentGateway paymentGateway, Product product, User buyer) {
        this.quantity = quantity;
        this.paymentGateway = paymentGateway;
        this.product = product;
        this.buyer = buyer;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public Product getProduct() {
        return product;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getProductOwner() {
        return product.getOwner();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String generatePaymentUrl(UriComponentsBuilder uriBuilder) {
        return this.paymentGateway.getGateway().generateUrl(this, uriBuilder);
    }

    public Boolean decreaseInventory(Integer quantity) {
        return product.decreaseInventory(quantity);
    }
}
