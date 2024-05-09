package com.example.democuatao.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_detail")
public class ProductDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Colors colors;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Sizes sizes;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "xuatxu_id")
    private XuatXu xuatXu;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private Float price;
    @Column(name = "name")
    private String name;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "productDetails")
    private List<ProductImages> productImages;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected  void onUpdate(){
        updatedAt = LocalDateTime.now();
    }




}
