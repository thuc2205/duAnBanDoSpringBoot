package com.example.democuatao.controller;

import com.example.democuatao.Service.BrandServiceImpl;
import com.example.democuatao.dtos.BrandDTO;
import com.example.democuatao.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandServiceImpl brandService;

    @PostMapping("")
    public ResponseEntity<?> createBrand(@ModelAttribute BrandDTO brandDTO, BindingResult result){

        if(result.hasErrors()){
            List<String> err = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
        try {
            brandService.create(brandDTO);
            return ResponseEntity.ok().body("tao brand thanh cong");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable int id,@RequestBody BrandDTO brandDTO){
        Brand brand = brandService.update(id,brandDTO);
        return ResponseEntity.ok(brand);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        brandService.delete(id);
        return ResponseEntity.ok("thanh COng");
    }

}
