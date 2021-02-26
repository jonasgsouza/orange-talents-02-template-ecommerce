package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.ProductImage;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailResponse {
    private String name;
    private BigDecimal price;
    private String description;
    private BigDecimal averageRate;
    private Integer totalRating;
    private List<String> links;
    private List<OpinionResponse> opinions;
    private List<QuestionResponse> questions;
    private List<CharacteristicResponse> characteristics;

    public ProductDetailResponse(Product product) {
        this.links = product.getImages().stream().map(ProductImage::getLink).collect(Collectors.toList());
        this.name = product.getName();
        this.price = product.getPrice();
        this.characteristics = product.getCharacteristics().stream().map(CharacteristicResponse::new).collect(Collectors.toList());
        this.description = product.getDescription();
        this.averageRate = product.averageRate();
        this.totalRating = product.totalRating();
        this.opinions = product.getOpinions().stream().map(OpinionResponse::new).collect(Collectors.toList());
        this.questions = product.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList());
    }

    public List<String> getLinks() {
        return Collections.unmodifiableList(links);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<CharacteristicResponse> getCharacteristics() {
        return Collections.unmodifiableList(characteristics);
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAverageRate() {
        return averageRate;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public List<OpinionResponse> getOpinions() {
        return Collections.unmodifiableList(opinions);
    }

    public List<QuestionResponse> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}
