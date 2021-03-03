package br.com.zup.mercadolivre.model;

import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private PurchaseStatus status = PurchaseStatus.INITIATED;

    @Enumerated(EnumType.STRING)
    @Column(name = "gateway_pagamento")
    private PaymentGateway paymentGateway;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private User buyer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

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

    public PurchaseStatus getStatus() {
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

    public void addTransaction(Transaction transaction) {
        Assert.isTrue(hasNoSucceededTransaction(), "Já existe uma transação finalizada com sucesso");
        Assert.isTrue(transactions.add(transaction),
                "Transação com id " + transaction.getGatewayTransactionId() + " já foi processada");
        if (transaction.succeeded()) status = PurchaseStatus.FINISHED;
    }

    private boolean hasNoSucceededTransaction() {
        return transactions.stream().filter(Transaction::succeeded).collect(Collectors.toSet()).isEmpty();
    }

    public String generatePaymentUrl(UriComponentsBuilder uriBuilder) {
        return this.paymentGateway.getGateway().generateUrl(this, uriBuilder);
    }

    public Boolean decreaseProductInventory() {
        return product.decreaseInventory(quantity);
    }

    public Boolean isFinished() {
        return status == PurchaseStatus.FINISHED;
    }

    public Set<Transaction> getTransactions() {
        return Collections.unmodifiableSet(transactions);
    }
}
