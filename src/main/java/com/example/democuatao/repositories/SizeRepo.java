package com.example.democuatao.repositories;

import com.example.democuatao.model.Sizes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepo extends JpaRepository<Sizes,Integer> {
}
