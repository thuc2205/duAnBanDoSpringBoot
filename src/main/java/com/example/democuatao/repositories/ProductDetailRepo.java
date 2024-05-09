package com.example.democuatao.repositories;

import com.example.democuatao.model.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepo extends JpaRepository<ProductDetails,Integer> {

    Page<ProductDetails> findAll(Pageable pageable);

    @Query("SELECT p FROM ProductDetails p Where p.id IN :productId")
    List<ProductDetails> findAllByIds(@Param("productId")List<Integer> productId);

}