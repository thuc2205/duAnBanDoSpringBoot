package com.example.democuatao.Service;

import com.example.democuatao.dtos.XuatXuDTO;
import com.example.democuatao.model.XuatXu;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IXuatXuService {
    XuatXu create(XuatXuDTO xuatXuDTO);

    XuatXu getById(int id);

    public CompletableFuture<List<XuatXu>> getAll();

    XuatXu update(int id , XuatXuDTO xuatXuDTO);

    void delete(int id);
}
