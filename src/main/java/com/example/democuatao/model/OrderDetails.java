package com.example.democuatao.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details")
@Builder
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetails productDetails;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Column(name = "price")
    private Float price;

    @Column(name = "number_of_products")
    private int quantifyProduct;

    @Column(name = "total_money")
    private Float totalMoney;



}
