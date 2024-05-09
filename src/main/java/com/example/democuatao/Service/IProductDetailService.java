package com.example.democuatao.Service;

import com.example.democuatao.dtos.ProductDetailDTO;
import com.example.democuatao.dtos.ProductImageDTO;
import com.example.democuatao.model.ProductDetails;
import com.example.democuatao.model.ProductImages;
import com.example.democuatao.responese.ProductResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductDetailService {
    public List<ProductDetails> create(ProductDetailDTO productDetailDTO) throws Exception;

    Page<ProductResponse> getAllProduct(PageRequest pageRequest);

    public List<ProductDetails> findProductsById(List<Integer> productId);

    public ProductDetails getProductsById(int productId);

    ProductImages createProductImage(int productId, ProductImageDTO productImageDTO) throws IllegalAccessException;
}
