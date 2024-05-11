package com.example.democuatao.controller;
import com.example.democuatao.Service.OrderServiceImpl;
import com.example.democuatao.dtos.OrderDTO;
import com.example.democuatao.model.Orders;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/order")
public class OrderController {
    private final OrderServiceImpl orderService;
    @PostMapping("")
    public ResponseEntity<?> createUOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result){
        try {
        if(result.hasErrors()){
            List<String> Order = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
            Orders orders = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
        @GetMapping("/findByUserId/{userId}")
        public String findOrdersByUserId(@PathVariable int userId, Model model) {
            List<Orders> orders = orderService.findByUserId(userId);
            model.addAttribute("orders",orders);
            return "layoutUsers/gioHang";

    }
}
