package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("password")
    private String password;
}
