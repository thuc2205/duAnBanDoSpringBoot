package com.example.democuatao.Service;

import com.example.democuatao.dtos.CartItemDTO;
import com.example.democuatao.dtos.OrderDTO;
import com.example.democuatao.model.OrderDetails;
import com.example.democuatao.model.Orders;
import com.example.democuatao.model.ProductDetails;
import com.example.democuatao.model.User;
import com.example.democuatao.repositories.OrderRepo;
import com.example.democuatao.repositories.OrderdetailRepo;
import com.example.democuatao.repositories.ProductDetailRepo;
import com.example.democuatao.repositories.UserRepo;

import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService{

    private final OrderRepo orderRepo;
    private final OrderdetailRepo orderdetailRepo;
    private final UserRepo userRepo;
    private final ProductDetailRepo productDetailRepo;

    @Override
    @Transactional
    public Orders createOrder(OrderDTO orderDTO) throws Exception {
        User user = userRepo.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFound("Khong tim thay id user : " +orderDTO.getUserId()));
        Date shippingDate = orderDTO.getShippingDate() == null ? new Date() : orderDTO.getShippingDate();
        Date today = new Date();
        if(shippingDate.before(today)){
            throw new IllegalAccessException("khong duoc be hon ngay hnay");
        }
        Orders newOrders = Orders.builder()
                .email(orderDTO.getEmail())
                .fullName(orderDTO.getFullName())
                .note(orderDTO.getNote())
                .paymentMethod(orderDTO.getPayMentmethod())
                .shippingMethod(orderDTO.getShippingMethod())
                .phoneNumber(orderDTO.getPhoneNumber())
                .totalMoney(orderDTO.getTotalMoney())
                .shippingAddres(orderDTO.getShippingAdress())
                .status("đang chơ xử lý")
                .orderDate(new Date())
                .active(true)
                .user(user)
                .build();
        orderRepo.save(newOrders);
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItemDTOS()){
            int productId = cartItemDTO.getProductId();
            int quantyfy = cartItemDTO.getQuantify();
            ProductDetails productDetails = productDetailRepo.findById(productId)
                    .orElseThrow(() ->new DataNotFound("Khong tim thay id pro duct" + productId));
            OrderDetails orderDetails =  OrderDetails.builder()
                    .orders(newOrders)
                    .productDetails(productDetails)
                    .quantifyProduct(quantyfy)
                    .price(productDetails.getPrice())
                    .totalMoney(quantyfy * productDetails.getPrice())
                    .build();
            orderDetailsList.add(orderDetails);
        }
        orderdetailRepo.saveAll(orderDetailsList);

        return newOrders;
    }

    @Override
    public Orders getOrderById(int id) {
        return null;
    }

    @Override
    public List<Orders> findByUserId(int userId) {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public Orders updateOrder(int id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(int id) {

    }
}
