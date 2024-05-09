package com.example.democuatao.Service;

import com.example.democuatao.dtos.SizeDTO;
import com.example.democuatao.model.Sizes;
import com.example.democuatao.repositories.SizeRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements ISizeService{
    private final SizeRepo sizeRepo;

    @Transactional
    @Override
    public Sizes createSize(SizeDTO sizeDTO) {
        Sizes sizes = Sizes.builder()
                .name(sizeDTO.getName())
                .build();
        return sizeRepo.save(sizes);
    }

    @Override
    public Sizes getSizeById(int id) {
        return sizeRepo.findById(id)
                .orElseThrow(() -> new ResolutionException("Deo Tim Thay Size"));
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<Sizes>> getAllSizeAsync() {
        return CompletableFuture.completedFuture(sizeRepo.findAll());
    }

    @Override
    public List<Sizes> getAllSize() {
        return sizeRepo.findAll();
    }

    @Transactional
    @Override
    public Sizes updateSize(int id, SizeDTO sizes) {
        Sizes existingSize = getSizeById(id);
        existingSize.setName(sizes.getName());
        return existingSize;
    }

    @Transactional
    @Override
    public void deleteSize(int id) {
        sizeRepo.deleteById(id);
    }
}
