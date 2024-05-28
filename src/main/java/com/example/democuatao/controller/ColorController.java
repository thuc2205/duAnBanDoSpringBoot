package com.example.democuatao.controller;

import com.example.democuatao.Service.ColorServiceImpl;
import com.example.democuatao.dtos.ColorDTO;

import com.example.democuatao.model.Colors;
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
@RequestMapping("${api.prefix}/color")
@RequiredArgsConstructor
public class ColorController {

    private final ColorServiceImpl colorService;
    @PostMapping("")
    public ResponseEntity<?> createColor(@Valid @RequestBody ColorDTO colorDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errCreateColor = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
        colorService.create(colorDTO);
        return ResponseEntity.ok().body("Thành cong tao mau");
    }





    @PutMapping({"/{id}"})
    public ResponseEntity<?> update(@PathVariable int id, @ModelAttribute ColorDTO colorDTO){
        Colors colors = colorService.update(id,colorDTO);
        return ResponseEntity.ok(colors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        colorService.delete(id);
        return ResponseEntity.ok("Xóa Thành Công");
    }
}
