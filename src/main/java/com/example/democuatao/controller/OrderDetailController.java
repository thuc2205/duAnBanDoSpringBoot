package com.example.democuatao.controller;

import com.example.democuatao.Service.OrderDetailServiceImpl;
import com.example.democuatao.dtos.OrderDetailDTO;
import com.example.democuatao.model.OrderDetails;
import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orderdetail")
public class OrderDetailController {

    private final OrderDetailServiceImpl orderDetailService;

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




}
