package com.example.democuatao.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VoucherDTO {
    @JsonProperty("code_voucher")
    @NotNull(message = "Khong trong ma voucher")
    private String codeVoucher;

    @NotNull(message = "Khong trong name voucher")
    @JsonProperty("name_km")
    private String nameKm;

    @JsonProperty("gia_tri_toi_thieu")
    @Min(value = 0,message = "gia tri toi thieu lon hon > 0")
    private Float giaTriToiThieu;

    @NotNull(message = "Khong trong giamGia voucher")
    @JsonProperty("giam_gia")
    private Float giamGia;

    @Min(value = 1,message = "gia tri toi thieu lon hon > 1")
    private int quantify;

    @JsonProperty("start_day")
    private Date startDay;

    @JsonProperty("end_day")
    private Date endDay;

    private String status;
}
