package com.example.democuatao.ExpirationChecked;

import com.example.democuatao.Service.VoucherServiceImpl;
import com.example.democuatao.model.Voucher;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class VoucherExpirationChecker {
    private final VoucherServiceImpl voucherService;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Scheduled(fixedDelay = 86400000) // Chạy mỗi ngày (1 ngày = 86400000 millisecond)
    @Transactional
    public void checkVoucherExpiration() {
        CompletableFuture<List<Voucher>> futureVouchers = voucherService.getAll();
        futureVouchers.thenAcceptAsync(vouchers -> {
            Date currentDate = new Date();
            for (Voucher voucher : vouchers) {
                if (voucher.getStarDay().equals(voucher.getEndDay()) && currentDate.after(voucher.getEndDay())) {
                    voucher.setStatus("Hết hạn");
//                    voucherService.updateVoucher(voucher);
                }
            }
        }, executorService);
    }
}
