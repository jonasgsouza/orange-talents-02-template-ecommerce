package br.com.zup.mercadolivre.model;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private Integer inventory;

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

    public Product(String name, BigDecimal price, Integer inventory, String description, Category category, User user) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
        this.owner = user;
    }

    public Product(String name, BigDecimal price, Integer inventory, Set<Characteristic> characteristics, String description, Category category, User owner, LocalDate createdAt) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
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

    public Integer getInventory() {
        return inventory;
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
        if (totalRating() == 0) return average;
        return average.divide(BigDecimal.valueOf(totalRating()));
    }

    public Integer totalRating() {
        return opinions.size();
    }

    public boolean decreaseInventory(Integer quantity) {
        Assert.isTrue(quantity > 0, "A quantidade deve ser maior que zero para abater o estoque");
        if (quantity <= this.inventory) {
            this.inventory -= quantity;
            return true;

        }
        return false;
    }
}
