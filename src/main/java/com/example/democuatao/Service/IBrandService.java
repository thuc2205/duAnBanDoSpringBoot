package com.example.democuatao.Service;

import com.example.democuatao.dtos.BrandDTO;
import com.example.democuatao.model.Brand;


import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IBrandService {
    Brand create(BrandDTO brandDTO);

    Brand getById(int id);

    public CompletableFuture<List<Brand>> getAll();

    Brand update(int id , BrandDTO brandDTO);

    void delete(int id);
}
