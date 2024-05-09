package com.example.democuatao.repositories;

import com.example.democuatao.model.ProductImages;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepo extends JpaRepository<ProductImages,Integer> {
    List<ProductImages> findByProductDetailsId(int productDetails);


}
