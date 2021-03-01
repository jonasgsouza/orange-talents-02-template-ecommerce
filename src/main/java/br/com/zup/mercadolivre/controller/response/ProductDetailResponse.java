package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ProductDetailResponse {
    private String name;
    private BigDecimal price;
    private String description;
    private BigDecimal averageRate;
    private Integer totalRating;
    private List<String> links;
    private List<OpinionResponse> opinions;
    private List<QuestionResponse> questions;
    private Set<CharacteristicResponse> characteristics;

    public ProductDetailResponse(Product product) {
        this.links = ProductImageResponse.mapList(product.getImages());
        this.name = product.getName();
        this.price = product.getPrice();
        this.characteristics = CharacteristicResponse.mapSet(product.getCharacteristics());
        this.description = product.getDescription();
        this.averageRate = product.averageRate();
        this.totalRating = product.totalRating();
        this.opinions = OpinionResponse.mapList(product.getOpinions());
        this.questions = QuestionResponse.mapList(product.getQuestions());
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

    public Set<CharacteristicResponse> getCharacteristics() {
        return Collections.unmodifiableSet(characteristics);
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
