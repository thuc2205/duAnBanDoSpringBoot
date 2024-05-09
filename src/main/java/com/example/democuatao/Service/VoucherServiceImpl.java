package com.example.democuatao.Service;

import com.example.democuatao.dtos.VoucherDTO;
import com.example.democuatao.model.Voucher;
import com.example.democuatao.repositories.VoucherRepo;
import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements IVoucherService{
    private final VoucherRepo voucherRepo;
    @Override
    @Transactional
    public Voucher create(VoucherDTO voucherDTO) {
        try {
            Date now = new Date();
            if (voucherDTO.getStartDay().after(now)) {
                throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày hiện tại");
            }
            if (voucherDTO.getEndDay().before(now)) {
                throw new IllegalArgumentException("Ngày kết thúc phải lớn hơn ngày hiện tại");
            }
            if (voucherDTO.getStartDay().after(voucherDTO.getEndDay())) {
                throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc");
            }
            if (voucherDTO.getEndDay().before(voucherDTO.getStartDay())) {
                throw new IllegalArgumentException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
            }
            Voucher voucher = Voucher.builder()
                    .codeVoucher(rollCodeVoucher())
                    .namKm(voucherDTO.getNameKm())
                    .quantify(voucherDTO.getQuantify())
                    .giaTriToiThieu(voucherDTO.getGiaTriToiThieu())
                    .giamGia(voucherDTO.getGiamGia())
                    .starDay(voucherDTO.getStartDay())
                    .endDay(voucherDTO.getEndDay())
                    .status("tạo mới")
                    .build();
           return voucherRepo.save(voucher);
        }catch (Exception e){
            e.printStackTrace();
            throw e;

        }

    }

    private String rollCodeVoucher() {
        Random random = new Random();
        StringBuilder ma = new StringBuilder("VC");
        for (int i = 0; i < 10; i++) {
            ma.append(random.nextInt(10));
        }
        return ma.toString();
    }


    @Override
    public Voucher getById(int id) throws DataNotFound {
        return voucherRepo.findById(id).orElseThrow(() -> new DataNotFound("Deo Tim Thay"));
    }

    @Override
    public CompletableFuture<List<Voucher>> getAll() {
        return CompletableFuture.completedFuture(voucherRepo.findAll());
    }

    @Override
    public Voucher update(int id, VoucherDTO voucher) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
