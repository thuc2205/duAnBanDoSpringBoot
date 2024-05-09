package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("quantify")
    private int quantify;
}
