package com.example.democuatao.controller;

import com.example.democuatao.Service.OrderDetailServiceImpl;
import com.example.democuatao.Service.OrderServiceImpl;
import com.example.democuatao.dtos.OrderDetailDTO;
import com.example.democuatao.model.OrderDetails;
import com.example.democuatao.model.Orders;
import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("${api.prefix}/orderdetail")
public class OrderDetailController {

    private final OrderDetailServiceImpl orderDetailService;
    private final OrderServiceImpl orderService;

    @PostMapping("")
    public ResponseEntity<?> createUOrder(@Valid @ModelAttribute OrderDetailDTO orderDetailDTO,
                                          BindingResult result, Model model , HttpServletRequest request){
        try {
            if(result.hasErrors()){
                List<String> Order = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
            }
            orderDetailService.createOrder(orderDetailDTO,request);
            return ResponseEntity.ok(orderDetailDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
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





}
