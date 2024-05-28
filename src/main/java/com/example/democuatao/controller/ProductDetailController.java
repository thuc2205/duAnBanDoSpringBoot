package com.example.democuatao.controller;

import com.example.democuatao.Service.*;
import com.example.democuatao.dtos.ProductDetailDTO;
import com.example.democuatao.dtos.ProductImageDTO;
import com.example.democuatao.model.ProductDetails;
import com.example.democuatao.model.ProductImages;
import com.example.democuatao.repositories.ProductDetailRepo;
import com.example.democuatao.responese.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/productDetail")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetaiServiceimpl productDetaiServiceimpl;
    private final CategoryServiceImpl categoryService;
    private final XuatXuServiceImpl xuatXuService;
    private final BrandServiceImpl brandService;
    private final SizeServiceImpl sizeService;
    private final ColorServiceImpl colorService;
    private final ProductDetailRepo productDetailRepo;






//    @GetMapping("{id}")
//    public ResponseEntity<?>getOne(@PathVariable int id ,Model model){
//        ProductDetails productDetails=productDetaiServiceimpl.getProductsById(id);
//        model.addAttribute("productDetails",productDetails);
//    }


    @PostMapping("")
    public String createProductDetail(@RequestBody @ModelAttribute ProductDetailDTO productDetailDTO, BindingResult result) throws Exception {
        if(result.hasErrors()){
            List<String> errCreateProduct = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
        productDetaiServiceimpl.create(productDetailDTO);
        return "redirect:/api/thuc/productDetail/list";
    }
    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@ModelAttribute("files")List<MultipartFile> files,
    @PathVariable("id") int productDetailId) throws IllegalAccessException, IOException {

        try{
            ProductDetails existingProductDetails = productDetaiServiceimpl.getProductsById(productDetailId);
            if(files.size() > 5){
                return ResponseEntity.badRequest().body("lỗi file");
            }

            List<ProductImages> productImagesList = new ArrayList<>();
            for (MultipartFile file : files){
                if(file!=null && file.getSize() > 0){
                    if(file.getSize() > 10*1024*1024){
                        throw new IllegalAccessException("file qua 10mb");
                    }
                    String fileName = kiemTraFile(file);
                    ProductImages productImages = productDetaiServiceimpl.createProductImage(existingProductDetails.getId(),
                            ProductImageDTO.builder()
                                    .imageUrl(fileName)
                                    .build()
                    );
                    productImagesList.add(productImages);
                    ProductImages firstImage = productImagesList.get(0);
                    existingProductDetails.setThumbnail(firstImage.getUrl());
                    productDetailRepo.save(existingProductDetails);



                }
            }
            return ResponseEntity.ok(productImagesList);
        }catch (Exception e){
            ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.badRequest().body("lỗi");
    }
    private String kiemTraFile(MultipartFile file) throws IOException {
        if (file.getOriginalFilename() == null) {
            throw new IOException("Không phải ảnh");
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String uniqueFileName = java.util.UUID.randomUUID().toString() + "_" + fileName;

        Path fileUpload = Paths.get("upload");
        if (!Files.exists(fileUpload)) {
            Files.createDirectory(fileUpload);
        }
        Path destination = Paths.get(fileUpload.toString(), uniqueFileName);

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get("upload", imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }




}
