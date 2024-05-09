package com.example.democuatao.Service;

import com.example.democuatao.dtos.SizeDTO;
import com.example.democuatao.model.Sizes;



import java.util.List;

public interface ISizeService {
    Sizes createSize(SizeDTO sizeDTO);

    Sizes getSizeById(int id);

    List<Sizes> getAllSize();

    Sizes updateSize(int id , SizeDTO sizes);

    void deleteSize(int id);
}
