package com.example.democuatao.dtos;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String note;

    private String status;

    private Float totalMoney;

    private String shippingMethod;

    private String shippingAdress;

    private Date shippingDate;

    private Date orderDate;

    private String payMentmethod;

    private boolean active;

    List<CartItemDTO> cartItemDTOS;


}
