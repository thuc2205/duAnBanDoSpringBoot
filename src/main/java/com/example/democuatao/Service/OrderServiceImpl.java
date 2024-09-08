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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public Orders createOrder(@ModelAttribute OrderDTO orderDTO) throws Exception {
//        User user = userRepo.findById(orderDTO.getUserId())
//                .orElse(null);
//        Date shippingDate = orderDTO.getShippingDate() == null ? new Date() : orderDTO.getShippingDate();
//
        Orders newOrders = Orders.builder()
                .email(orderDTO.getEmail())
                .fullName(orderDTO.getFullName())
                .note(orderDTO.getNote())
                .paymentMethod(orderDTO.getPayMentmethod())
                .shippingMethod(orderDTO.getShippingMethod())
                .phoneNumber(orderDTO.getPhoneNumber())
                .totalMoney(orderDTO.getTotalMoney())
                .shippingAddres(orderDTO.getShippingAdress())
                .status("chờ xác nhận")
                .orderDate(new Date())
                .active(true)
//                .user(user)
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
    @Transactional
    public Orders updateOrderStatus(int id) throws DataNotFound {
        Orders existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new DataNotFound("Khong tim thay id order " + id));
        existingOrder.setStatus("Đang Chuẩn Bị");
        return existingOrder;
    }

    @Override
    public Orders getOrderById(int id) {
        return null;
    }

    @Override
    public List<Orders> findByUserId(String phoneNumber) {
        return orderRepo.findOrdersByUserPhoneNumberAndStatus(phoneNumber);
    }
    public Page<Orders> findByStatus(Pageable pageable) {
        return orderRepo.findOrdersByStatus(pageable);
    }
    public Page<Orders> findByStatus2(Pageable pageable) {
        return orderRepo.findOrdersByStatus2(pageable);
    }
    public Page<Orders> findByStatus3(Pageable pageable) {
        return orderRepo.findOrdersByStatus3(pageable);
    }

    @Override
    @Transactional
    public Orders updateOrder(int id, OrderDTO orderDTO) throws DataNotFound {
        Orders existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new DataNotFound("Khong tim thay id order " + id));

        existingOrder.setEmail(orderDTO.getEmail());
        existingOrder.setFullName(orderDTO.getFullName());
        existingOrder.setNote(orderDTO.getNote());
        existingOrder.setPaymentMethod(orderDTO.getPayMentmethod());
        existingOrder.setShippingMethod(orderDTO.getShippingMethod());
        existingOrder.setPhoneNumber(orderDTO.getPhoneNumber());
        existingOrder.setTotalMoney(orderDTO.getTotalMoney());
        existingOrder.setShippingAddres(orderDTO.getShippingAdress());
        existingOrder.setStatus("chờ xác nhận");
        existingOrder.setOrderDate(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : existingOrder.getOrderDate());
        existingOrder.setActive(true);
        return existingOrder;
    }    @Transactional
    public Orders updateOrderStatus(int id, String newStatus) throws DataNotFound {
        Orders existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new DataNotFound("Không tìm thấy đơn hàng với ID " + id));

        // Set new status
        existingOrder.setStatus(newStatus);

        return orderRepo.save(existingOrder);
    }

    @Override
    public void deleteOrder(int id) {

    }


}
