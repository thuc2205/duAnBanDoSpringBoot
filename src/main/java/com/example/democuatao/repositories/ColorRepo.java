package com.example.democuatao.repositories;


import com.example.democuatao.model.Colors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepo extends JpaRepository<Colors,Integer> {
}
