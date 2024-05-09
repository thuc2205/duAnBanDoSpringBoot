package com.example.democuatao.responese;

import com.example.democuatao.model.ProductDetails;
import com.example.democuatao.model.ProductImages;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private int id;

    private String name;

    private String thumbnail;

    private Float price;

    @JsonProperty("category_id")
    private String categoryId;

    private String size;

    private String brand;

    private String xuatxu;
    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("update_at")
    private LocalDateTime updatedAt;

    private List<ProductImages> productImages = new ArrayList<>();

    public static ProductResponse fromProduct(ProductDetails products){
        ProductResponse productResponse = ProductResponse.builder()
                .id(products.getId())
                .name(products.getName())
                .price(products.getPrice())
                .categoryId(products.getCategories().getName())
                .productImages(products.getProductImages())
                .size(products.getSizes().getName())
                .brand(products.getBrand().getName())
                .xuatxu(products.getXuatXu().getName())
                .description(products.getDescription())
                .thumbnail(products.getThumbnail())
                .build();
        productResponse.setCreatedAt(products.getCreatedAt());
        productResponse.setUpdatedAt(products.getUpdatedAt());
        return productResponse;
    }

}
