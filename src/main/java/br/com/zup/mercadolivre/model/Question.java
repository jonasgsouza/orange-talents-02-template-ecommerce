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
    @JoinColumn(name = "usuario_id")
    private User user;

    @Column(name = "dataCriacao")
    private LocalDate createdAt = LocalDate.now();

    @Deprecated
    public Question() {
    }

    public Question(String title, Product product, User user) {
        this.title = title;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
