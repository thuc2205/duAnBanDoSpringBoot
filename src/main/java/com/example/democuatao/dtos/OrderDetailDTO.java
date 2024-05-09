package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailDTO {
    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("product_detail_id")
    private int productDetailId;

    private Float price;

    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @JsonProperty("total_money")
    private Float totalMoney;
}
