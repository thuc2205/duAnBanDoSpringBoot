package com.example.democuatao.Service;

import com.example.democuatao.dtos.OrderDetailDTO;
import com.example.democuatao.model.OrderDetails;


import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public interface IOrderDetailService {
    public OrderDetails createOrder(OrderDetailDTO orderDetailDTO, HttpServletRequest request) throws DataNotFound  ;
    OrderDetails getOrderById(int id);

//    List<OrderDetails> findByUserId(int userId);
OrderDetails updateOrder(int id, OrderDetailDTO orderDetailDTO);

    void deleteOrder(int id);
}
