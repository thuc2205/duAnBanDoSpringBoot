package com.example.democuatao.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voucher")
@Builder
public class Voucher {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code_voucher")
    private String codeVoucher;

    @Column(name = "namekm")
    private String namKm;

    @Column(name = "giatritoithieu")
    private Float giaTriToiThieu;

    @Column(name = "giamgia")
    private Float giamGia;

    @Column(name = "quantify")
    private int quantify;

    @Column(name = "star_day")
    private Date starDay;

    @Column(name = "end_day")
    private Date endDay;

    @Column(name = "status")
    private String status;




}
