package com.example.democuatao.repositories;

import com.example.democuatao.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Integer> {

    List<Orders> findByUserId(int id);

    Orders findByUser_Id(int id);
}
