package com.example.democuatao.Service;

import com.example.democuatao.dtos.VoucherDTO;
import com.example.democuatao.model.Voucher;
import com.shopcuatao.bangiay.exeption.DataNotFound;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IVoucherService {
    Voucher create(VoucherDTO voucherDTO);

    Voucher getById(int id) throws DataNotFound;

    public CompletableFuture<List<Voucher>> getAll();

    Voucher update(int id , VoucherDTO voucher);

    void delete(int id);
}
