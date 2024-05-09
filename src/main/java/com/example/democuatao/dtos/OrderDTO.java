package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {
    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("email")
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String note;

    private String status;
    @Min(value = 0,message = "tong tien >0")
    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_adress")
    private String shippingAdress;

    @JsonProperty("shipping_date")
    private Date shippingDate;

    @JsonProperty( "payment_method")
    private String payMentmethod;

    private boolean active;

    @JsonProperty("cart_item")
    List<CartItemDTO> cartItemDTOS;


}
