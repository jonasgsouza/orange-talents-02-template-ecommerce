package br.com.zup.mercadolivre.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "transacoes")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true)
    private String gatewayTransactionId;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Purchase purchase;

    @Column(name = "dataCriacao")
    private LocalDate createdAt = LocalDate.now();

    @Column(updatable = false)
    private TransactionStatus status;

    @Deprecated
    public Transaction() {
    }

    public Transaction(String gatewayTransactionId, Purchase purchase, TransactionStatus status) {
        this.gatewayTransactionId = gatewayTransactionId;
        this.purchase = purchase;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public boolean succeeded() {
        return status == TransactionStatus.SUCCEEDED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return gatewayTransactionId.equals(that.gatewayTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gatewayTransactionId);
    }
}
