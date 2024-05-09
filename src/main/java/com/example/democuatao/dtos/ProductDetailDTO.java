package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetailDTO {

    private String name;

    @JsonProperty("category_id")
    private int categoryId;

    @JsonProperty("brand_id")
    private int brandId;

    @JsonProperty("xuatxu_id")
    private int xuatxuId;


    private String thumbnail;

    private String description;

    private int quantity;

    @Min(value = 0,message = "khong duoc 0 gia")
    private Float price;
    private List<listSizeColor> listSizeColors;

}
