package com.example.democuatao.Service;

import com.example.democuatao.dtos.CategoriesDto;
import com.example.democuatao.model.Categories;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface ICategoryService {
    Categories create(CategoriesDto categoriesDto);

    Categories getById(int id);

    public CompletableFuture<List<Categories>> getAll();

    Categories update(int id , CategoriesDto categoriesDto);

    void delete(int id);
}
