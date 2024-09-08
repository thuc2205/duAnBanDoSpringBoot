package com.example.democuatao.repositories;

import com.example.democuatao.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Integer> {

    @Query("SELECT o FROM Orders o WHERE o.user.phoneNumber = :phoneNumber AND o.status = 'đang chờ xử lý'")
    List<Orders> findOrdersByUserPhoneNumberAndStatus(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT o FROM Orders o WHERE o.status = 'chờ xác nhận'")
    Page<Orders> findOrdersByStatus(Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE o.status = 'Đang Chuẩn Bị'")
    Page<Orders> findOrdersByStatus2(Pageable pageable);
    @Query("SELECT o FROM Orders o WHERE o.status = 'Đang Giao Hàng'")
    Page<Orders> findOrdersByStatus3(Pageable pageable);

    Orders findByUserId(int userId);

    @Query("SELECT SUM(od.totalMoney) FROM OrderDetails od JOIN od.orders o " +
            "WHERE o.status = 'Thành Công' AND function('DATE', o.orderDate) = CURRENT_DATE")
    Float getTotalMoneyForSuccessfulOrdersToday();





    @Query("SELECT o.id FROM Orders o WHERE o.user.id = :userId AND o.status = 'đang chờ xử lý'")
    List<Integer> findOrderIdWhereUserIdAndStatus(@Param("userId") int userId);

}
