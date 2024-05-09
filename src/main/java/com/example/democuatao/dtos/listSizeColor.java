package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class listSizeColor {
    private int sizeId;
    private int colorId;
}
