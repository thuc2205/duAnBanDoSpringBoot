package com.example.democuatao.Service;

import com.example.democuatao.dtos.OrderDTO;
import com.example.democuatao.model.Orders;
import com.shopcuatao.bangiay.exeption.DataNotFound;


import java.util.List;

public interface IOrderService {
    Orders createOrder(OrderDTO orderDTO)throws Exception;

    Orders getOrderById(int id);

    List<Orders>findByUserId(String phoneNumber);
    Orders updateOrder(int id, OrderDTO orderDTO) throws DataNotFound;

    void deleteOrder(int id);
}
