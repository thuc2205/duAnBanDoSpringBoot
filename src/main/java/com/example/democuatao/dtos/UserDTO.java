package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "Khong trong phone")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @NotBlank(message = "khong trong password")
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "khong trong retype password")
    @JsonProperty("retype_password")
    private String retypePassword;

    private String status;

    private Date birth;

    @JsonProperty("facebook_id")
    private int facebookId;

    @JsonProperty("google_id")
    private int googleId;

    @NotNull(message = "chon roll")
    @JsonProperty("role_id")
    private int roleId;

}
