package com.example.democuatao.Service;

import com.example.democuatao.dtos.OrderDTO;
import com.example.democuatao.model.Orders;


import java.util.List;

public interface IOrderService {
    Orders createOrder(OrderDTO orderDTO)throws Exception;

    Orders getOrderById(int id);

    List<Orders>findByUserId(int userId);
    Orders updateOrder(int id, OrderDTO orderDTO);

    void deleteOrder(int id);
}
