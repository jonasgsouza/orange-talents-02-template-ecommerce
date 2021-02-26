package br.com.zup.mercadolivre.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "perguntas")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String title;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private User owner;

    @Column(name = "dataCriacao")
    private LocalDate createdAt = LocalDate.now();

    @Deprecated
    public Question() {
    }

    public Question(String title, Product product, User owner) {
        this.title = title;
        this.owner = owner;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getOwner() {
        return owner;
    }

    public Product getProduct() {
        return product;
    }

    public User getProductOwner() {
        return product.getOwner();
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
