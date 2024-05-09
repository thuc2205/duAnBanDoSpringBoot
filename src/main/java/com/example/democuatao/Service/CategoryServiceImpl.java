package com.example.democuatao.Service;

import com.example.democuatao.dtos.CategoriesDto;
import com.example.democuatao.model.Categories;
import com.example.democuatao.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepo categoryRepo;

    @Override
    public Categories create(CategoriesDto categoriesDto) {
        Categories categories = Categories.builder()
                .name(categoriesDto.getName())
                .build();
        return categoryRepo.save(categories);
    }

    @Override
    public Categories getById(int id) {
        return categoryRepo.findById(id).orElseThrow(() -> new ResolutionException("Khong tim thay id : "+id));
    }

    @Override
    public CompletableFuture<List<Categories>> getAll() {
        return CompletableFuture.completedFuture(categoryRepo.findAll());
    }
    public List<Categories> getAllReal(){
        return categoryRepo.findAll();
    }

    @Override
    public Categories update(int id, CategoriesDto categoriesDto) {
        Categories existingCategories = getById(id);
        existingCategories.setName(categoriesDto.getName());
        return existingCategories;
    }

    @Override
    public void delete(int id) {
        Categories existingCategories = getById(id);
        categoryRepo.delete(existingCategories);
    }
}
