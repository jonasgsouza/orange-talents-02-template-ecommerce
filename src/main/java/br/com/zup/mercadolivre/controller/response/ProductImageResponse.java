package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

public class ProductImageResponse {

    public static List<String> mapList(List<ProductImage> images) {
        return images.stream().map(ProductImage::getLink).collect(Collectors.toList());
    }
}
