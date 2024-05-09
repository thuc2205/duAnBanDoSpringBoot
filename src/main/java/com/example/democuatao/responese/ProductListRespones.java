package com.example.democuatao.responese;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListRespones {
    private List<ProductResponse> productResponses;
    private int totalPage;
}
