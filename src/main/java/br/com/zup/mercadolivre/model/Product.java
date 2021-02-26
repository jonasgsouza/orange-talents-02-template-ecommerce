package br.com.zup.mercadolivre.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "preco")
    private BigDecimal price;

    @Column(name = "quantidade")
    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<Characteristic> characteristics = new HashSet<>();

    @Column(name = "descricao", length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private User owner;

    @Column(name = "data_criacao")
    private LocalDate createdAt = LocalDate.now();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Opinion> opinions = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Question> questions = new ArrayList<>();

    @Deprecated
    public Product() {
    }

    public Product(String name, BigDecimal price, Integer quantity, String description, Category category, User user) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.owner = user;
    }

    public Product(String name, BigDecimal price, Integer quantity, Set<Characteristic> characteristics, String description, Category category, User owner, LocalDate createdAt) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.characteristics.addAll(characteristics);
        this.description = description;
        this.category = category;
        this.owner = owner;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void addCharacteristic(Characteristic characteristic) {
        this.characteristics.add(characteristic);
    }

    public Set<Characteristic> getCharacteristics() {
        return Collections.unmodifiableSet(characteristics);
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void addImages(List<String> links) {
        var images = links.stream()
                .map(link -> new ProductImage(link, this))
                .collect(Collectors.toList());
        this.images.addAll(images);
    }

    public List<ProductImage> getImages() {
        return Collections.unmodifiableList(images);
    }

    public List<Opinion> getOpinions() {
        return Collections.unmodifiableList(opinions);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public BigDecimal averageRate() {
        var total = this.opinions.stream().mapToInt(o -> Integer.valueOf(o.getRate())).sum();
        var average = BigDecimal.valueOf(total);
        return average.divide(BigDecimal.valueOf(totalRating()));
    }

    public Integer totalRating() {
        return opinions.size();
    }
}
