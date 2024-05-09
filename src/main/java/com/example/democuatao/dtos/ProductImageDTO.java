package com.example.democuatao.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDTO {
    @JsonProperty("product_detail_id")
    @Min(value = 1,message = "product id must be > 0")
    private int productDetailId;

    @Size(min = 5,max = 200,message = "image name")
    @JsonProperty("image_url")
    private String imageUrl;
}
