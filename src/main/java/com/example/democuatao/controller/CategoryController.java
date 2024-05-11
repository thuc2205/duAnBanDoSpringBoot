package com.example.democuatao.controller;

import com.example.democuatao.Service.CategoryServiceImpl;
import com.example.democuatao.dtos.CategoriesDto;
import com.example.democuatao.model.Categories;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @PostMapping("")
    public String createCategory(@ModelAttribute CategoriesDto categoriesDto, BindingResult result){
        if(result.hasErrors()){
            List<String> err = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
        categoryService.create(categoriesDto);
        return "redirect:/api/thuc/categories";
    }
    @GetMapping("")
    public String getAllCategories(Model model){
        model.addAttribute("categories",categoryService.getAllReal());


        return "categories";
    }

    @PutMapping("/{id}")
        public ResponseEntity<?> updateCategory(@PathVariable int id,@RequestBody CategoriesDto categoriesDto){
        Categories categories = categoryService.update(id,categoriesDto);
        return ResponseEntity.ok(categories);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id){
        categoryService.delete(id);
        return ResponseEntity.ok("Xóa Thành CÔng");
    }




































































































}
