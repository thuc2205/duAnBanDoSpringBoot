package com.example.democuatao.controller;

import com.example.democuatao.Service.OrderServiceImpl;
import com.example.democuatao.dtos.OrderDTO;
import com.example.democuatao.model.Orders;
import com.example.democuatao.repositories.OrderRepo;
import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/order")
public class OrderController {
    private final OrderServiceImpl orderService;
    private final OrderRepo orderRepo;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable int id, @RequestParam String status) {
        try {
            // Call the service method to update order status
            Orders updatedOrder = orderService.updateOrderStatus(id, status);

            if (updatedOrder != null) {
                return ResponseEntity.ok(updatedOrder);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating order status: " + e.getMessage());
        }
    }

    @PostMapping("add")
    public ResponseEntity<?> createUOrder(@ModelAttribute OrderDTO orderDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Orders orders = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//        @PostMapping("/update/{userId}")
//    public ResponseEntity<Orders> updateOrderForUser(@PathVariable int userId, @ModelAttribute OrderDTO orderDTO) throws DataNotFound {
//        Integer orderId = orderRepo.findOrderIdWhereUserIdAndStatus(userId);
//        if (orderId != null) {
//            Orders updatedOrder = orderService.updateOrder(orderId, orderDTO);
//            return ResponseEntity.ok(updatedOrder);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
