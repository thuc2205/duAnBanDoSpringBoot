package com.example.democuatao.controller;

import com.example.democuatao.Service.SizeServiceImpl;
import com.example.democuatao.dtos.SizeDTO;
import com.example.democuatao.model.Sizes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${api.prefix}/size")
@RequiredArgsConstructor
@EnableWebMvc
public class SizeController {
    private final SizeServiceImpl sizeService;
    @PostMapping("")
    public String createCategory(@Valid  @ModelAttribute SizeDTO sizeDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
        sizeService.createSize(sizeDTO);
        return "redirect:/api/thuc/size/list";
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> updateSize(@PathVariable int id, @ModelAttribute SizeDTO sizeDTO){
       Sizes sizes= sizeService.updateSize(id,sizeDTO);
        return ResponseEntity.ok(sizes);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable int id,@ModelAttribute SizeDTO sizeDTO){
        sizeService.deleteSize(id);
        return ResponseEntity.ok("Xóa Thành Công");
    }






























}
