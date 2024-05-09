package com.example.democuatao.Service;

import com.example.democuatao.dtos.ColorDTO;
import com.example.democuatao.model.Colors;


import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface IColorService {
    Colors create(ColorDTO brandDTO);

    Colors getById(int id);

    public CompletableFuture<List<Colors>> getAll();

    Colors update(int id , ColorDTO colorDTO);

    void delete(int id);
}
