package com.example.democuatao.Service;

import com.example.democuatao.dtos.XuatXuDTO;
import com.example.democuatao.model.XuatXu;
import com.example.democuatao.repositories.XuatXuRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class XuatXuServiceImpl implements IXuatXuService{
    private final XuatXuRepo xuatXuRepo;

    @Transactional
    @Override
    public XuatXu create(XuatXuDTO xuatXuDTO) {
        XuatXu xuatXu = XuatXu.builder()
                .name(xuatXuDTO.getName())
                .build();
        return xuatXuRepo.save(xuatXu);
    }

    @Override
    public XuatXu getById(int id) {
        return xuatXuRepo.findById(id).orElseThrow(() -> new ResolutionException("khong thay "));
    }

    @Override
    public CompletableFuture<List<XuatXu>> getAll() {
        return CompletableFuture.completedFuture(xuatXuRepo.findAll());
    }
    public List<XuatXu> getAllReal(){
        return xuatXuRepo.findAll();
    }

    @Transactional
    @Override
    public XuatXu update(int id, XuatXuDTO xuatXuDTO) {
        XuatXu existingXuatXua = getById(id);
        existingXuatXua.setName(xuatXuDTO.getName());
        return existingXuatXua;
    }
    @Transactional
    @Override
    public void delete(int id) {

    }
}
