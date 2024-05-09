package com.example.democuatao.controller;

import com.example.democuatao.Service.XuatXuServiceImpl;
import com.example.democuatao.dtos.XuatXuDTO;
import com.example.democuatao.model.XuatXu;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/xuatxu")
@RequiredArgsConstructor
public class XuatXuController {

    private final XuatXuServiceImpl xuatXuService;

    @PostMapping("")
    public String createXuatXu(@ModelAttribute XuatXuDTO xuatXuDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errCreateXuatXu = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
        }
        xuatXuService.create(xuatXuDTO);
        return "redirect:/api/thuc/xuatxu";
    }
    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("xuatxu",xuatXuService.getAllReal());
       return "admins/xuatxu";
    }
    @PutMapping({"/{id}"})
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody XuatXuDTO xuatXuDTO){
        XuatXu xuatXu = xuatXuService.update(id,xuatXuDTO);
        return ResponseEntity.ok(xuatXu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        xuatXuService.delete(id);
        return ResponseEntity.ok("Xóa Thành Công");
    }

}
