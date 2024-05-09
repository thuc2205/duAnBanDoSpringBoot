package com.example.democuatao.repositories;

import com.example.democuatao.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Categories,Integer> {
}
