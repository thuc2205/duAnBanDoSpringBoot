package com.example.democuatao.repositories;

import com.example.democuatao.model.OrderDetails;

import com.example.democuatao.model.Orders;
import com.example.democuatao.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

public interface OrderdetailRepo extends JpaRepository<OrderDetails,Integer> {
    List<OrderDetails> findAllByOrders_Id(int orderId);

    Optional<OrderDetails> findByOrdersAndProductDetails(Orders orders, ProductDetails productDetails);


}
