package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailDTO {

    private int orderId;

    private int productDetailId;

    private Float price;

    private int numberOfProduct;

    private Float totalMoney;
}
