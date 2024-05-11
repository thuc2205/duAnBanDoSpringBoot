package com.example.democuatao.controller.user;

import com.example.democuatao.Service.ProductDetaiServiceimpl;
import com.example.democuatao.responese.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductDetaiServiceimpl productDetaiServiceimpl;

    @GetMapping("/home")
    public String displayUser(){
        return "layoutUsers/home";
    }
    @GetMapping("/gioHang")
    public String disPlayGioHang(){
        return "layoutUsers/gioHang";
    }

    @GetMapping("/dssp")
    public String getAll(
            @RequestParam(defaultValue = "0",name = "category_id")int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit
            , Model model
    ){
        PageRequest pageRequest;
        if(categoryId==0){
            pageRequest = PageRequest.of(page,limit);
        }else{
            pageRequest= PageRequest.of(page,limit, Sort.by("id").ascending());
        }
        Page<ProductResponse> productPage = productDetaiServiceimpl.getAllProduct(pageRequest);
        int totalPage= productPage.getTotalPages();
        List<ProductResponse> list = productPage.getContent();
        model.addAttribute("productList", list);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        return "layoutUsers/dssp";
    }
}
