package com.example.democuatao.Service;

import com.example.democuatao.dtos.ColorDTO;
import com.example.democuatao.model.Colors;
import com.example.democuatao.repositories.ColorRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class ColorServiceImpl implements IColorService{
   private final ColorRepo colorRepo;
    @Override
    @Transactional
    public Colors create(ColorDTO brandDTO) {
        Colors colors = Colors.builder()
                .name(brandDTO.getName())
                .build();
        return colorRepo.save(colors);
    }

    @Override
    public Colors getById(int id) {
        return colorRepo.findById(id).orElseThrow(() -> new RuntimeException("Khong Thay id"));
    }

    @Override
    public CompletableFuture<List<Colors>> getAll() {
        return CompletableFuture.completedFuture(colorRepo.findAll());
    }
    public List<Colors> getAllReal(){
        return colorRepo.findAll();
    }

    @Override
    public Colors update(int id, ColorDTO colorDTO) {
        Colors existingColor = getById(id);
        existingColor.setName(colorDTO.getName());
        return existingColor;
    }
    @Transactional
    @Override
    public void delete(int id) {
        Colors existingColor = getById(id);
        colorRepo.delete(existingColor);
    }

   
}
