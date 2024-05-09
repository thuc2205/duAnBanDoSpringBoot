package com.example.democuatao.Service;

import com.example.democuatao.dtos.ProductDetailDTO;
import com.example.democuatao.dtos.ProductImageDTO;
import com.example.democuatao.dtos.listSizeColor;
import com.example.democuatao.model.Brand;
import com.example.democuatao.model.Categories;
import com.example.democuatao.model.Colors;
import com.example.democuatao.model.ProductDetails;
import com.example.democuatao.model.ProductImages;
import com.example.democuatao.model.Sizes;
import com.example.democuatao.model.XuatXu;
import com.example.democuatao.repositories.BrandRepo;
import com.example.democuatao.repositories.CategoryRepo;
import com.example.democuatao.repositories.ColorRepo;
import com.example.democuatao.repositories.ProductDetailRepo;
import com.example.democuatao.repositories.ProductImageRepo;
import com.example.democuatao.repositories.SizeRepo;
import com.example.democuatao.repositories.XuatXuRepo;
import com.example.democuatao.responese.ProductResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetaiServiceimpl implements IProductDetailService{

    private final BrandRepo brandRepo;
    private final ColorRepo colorRepo;
    private final XuatXuRepo xuatXuRepo;
    private final SizeRepo sizeRepo;
    private final CategoryRepo categoryRepo;
    private final ProductDetailRepo productDetailRepo;
    private final ProductImageRepo productImageRepo;
    @Override
    @Transactional
    public List<ProductDetails> create(ProductDetailDTO productDetailDTO) throws Exception {
        Categories categories = categoryRepo.findById(productDetailDTO.getCategoryId())
                .orElseThrow(() -> new ResolutionException("Không tìm thấy cate với id: " + productDetailDTO.getCategoryId()));

        Brand brand = brandRepo.findById(productDetailDTO.getBrandId())
                .orElseThrow(() -> new ResolutionException("Không tìm thấy brand với id: " + productDetailDTO.getBrandId()));

        XuatXu xuatXu = xuatXuRepo.findById(productDetailDTO.getXuatxuId())
                .orElseThrow(() -> new ResolutionException("Không tìm thấy xuất xứ với id: " + productDetailDTO.getXuatxuId()));

        List<ProductDetails> productDetailsList = new ArrayList<>();

        for (listSizeColor sizeColor : productDetailDTO.getListSizeColors()) {
            Sizes size = sizeRepo.findById(sizeColor.getSizeId())
                    .orElseThrow(() -> new ResolutionException("Không tìm thấy size với id: " + sizeColor.getSizeId()));

            Colors color = colorRepo.findById(sizeColor.getColorId())
                    .orElseThrow(() -> new ResolutionException("Không tìm thấy màu sắc với id: " + sizeColor.getColorId()));

            String thumbnailPath = productDetailDTO.getThumbnail();

            ProductDetails newProductDetail = ProductDetails.builder()
                    .name(productDetailDTO.getName())
                    .categories(categories)
                    .brand(brand)
                    .xuatXu(xuatXu)
                    .price(productDetailDTO.getPrice())
                    .quantity(productDetailDTO.getQuantity())
                    .description(productDetailDTO.getDescription())
                    .thumbnail("")
                    .sizes(size)
                    .colors(color)
                    .build();

            productDetailsList.add(productDetailRepo.save(newProductDetail));
        }

        return productDetailsList;
    }


    @Transactional
    @Override
    public ProductImages createProductImage(int productdetailId, ProductImageDTO productImageDTO) throws IllegalAccessException {
        ProductDetails existingProducts = productDetailRepo.findById(productdetailId)
                .orElseThrow(() -> new ResolutionException("deo tim thay voi id : "
                        +productImageDTO.getProductDetailId()));
        ProductImages newProductImages = ProductImages.builder()
                .productDetails(existingProducts)
                .url(productImageDTO.getImageUrl())
                .build();
        int sizeImage = productImageRepo.findByProductDetailsId(productdetailId).size();
        if(sizeImage > 5){
            throw new IllegalAccessException("Anh > 5");
        }

        return productImageRepo.save(newProductImages);
    }

    @Override
    public Page<ProductResponse> getAllProduct(PageRequest pageRequest) {
        return productDetailRepo.findAll(pageRequest).map(ProductResponse::fromProduct);
    }

    @Override
    public List<ProductDetails> findProductsById(List<Integer> productId) {
        return null;
    }

    @Override
    public ProductDetails getProductsById(int productId) {
        return productDetailRepo.findById(productId).orElseThrow(() -> new ResolutionException("Khong tim thay product Id "));
    }

}
