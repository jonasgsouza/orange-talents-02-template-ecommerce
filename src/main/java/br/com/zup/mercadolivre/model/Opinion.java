package br.com.zup.mercadolivre.model;

import javax.persistence.*;

@Entity
@Table(name = "opinioes")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nota")
    private Short rate;

    @Column(name = "titulo")
    private String title;

    @Column(name = "descricao")
    private String description;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    @Deprecated
    public Opinion() {
    }

    public Opinion(Short rate, String title, String description, Product product, User user) {
        this.rate = rate;
        this.title = title;
        this.description = description;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Short getRate() {
        return rate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }
}
