package com.example.democuatao.Service;

import com.example.democuatao.dtos.OrderDetailDTO;
import com.example.democuatao.model.OrderDetails;


import com.shopcuatao.bangiay.exeption.DataNotFound;



public interface IOrderDetailService {
    OrderDetails createOrder(OrderDetailDTO orderDetailDTO) throws DataNotFound;
    OrderDetails getOrderById(int id);

//    List<OrderDetails> findByUserId(int userId);
OrderDetails updateOrder(int id, OrderDetailDTO orderDetailDTO);

    void deleteOrder(int id);
}
