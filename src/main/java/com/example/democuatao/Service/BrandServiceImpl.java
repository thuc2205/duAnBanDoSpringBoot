package com.example.democuatao.Service;

import com.example.democuatao.dtos.BrandDTO;
import com.example.democuatao.model.Brand;
import com.example.democuatao.repositories.BrandRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements IBrandService{

    private final BrandRepo brandRepo;

    @Override
    @Transactional
    public Brand create(BrandDTO brandDTO) {
        Brand brand = Brand.builder()
                .name(brandDTO.getName())
                .build();
        return brandRepo.save(brand);
    }

    @Override
    public Brand getById(int id) {
        return brandRepo.findById(id).orElseThrow(()-> new ResolutionException("Khong TIm thay id"));
    }

    @Async("excutorBrand")
    @Override
    public CompletableFuture<List<Brand>> getAll() {
        return CompletableFuture.completedFuture(brandRepo.findAll());
    }
    public List<Brand> getAllReal(){
       return brandRepo.findAll();
    }

    @Override
    @Transactional
    public Brand update(int id, BrandDTO brandDTO) {
        Brand existingBrand = getById(id);
        existingBrand.setName(brandDTO.getName());
        return existingBrand;
    }

    @Override
    @Transactional
    public void delete(int id) {
        Brand existingBrand = getById(id);
        brandRepo.delete(existingBrand);

    }
}
