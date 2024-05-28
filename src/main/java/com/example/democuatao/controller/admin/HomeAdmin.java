package com.example.democuatao.controller.admin;


import com.example.democuatao.Service.BrandServiceImpl;
import com.example.democuatao.Service.CategoryServiceImpl;
import com.example.democuatao.Service.ColorServiceImpl;
import com.example.democuatao.Service.OrderServiceImpl;
import com.example.democuatao.Service.ProductDetaiServiceimpl;
import com.example.democuatao.Service.SizeServiceImpl;
import com.example.democuatao.Service.XuatXuServiceImpl;
import com.example.democuatao.model.Orders;
import com.example.democuatao.responese.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/layout")
@RequiredArgsConstructor
public class HomeAdmin {
    private final CategoryServiceImpl categoryService;
    private final ProductDetaiServiceimpl productDetaiServiceimpl;
    private final BrandServiceImpl brandService;
    private final ColorServiceImpl colorService;
    private final OrderServiceImpl orderService;
    private final SizeServiceImpl sizeService;
    private final XuatXuServiceImpl xuatXuService;

    @GetMapping("")
    public String displayAdmin(){
        return "layouts/admin";
    }
    @GetMapping("login")
    public String displayLogin(){
        return "layoutUsers/login";
    }

    @GetMapping("color")
    public String getAll(Model model) {
        model.addAttribute("colors",colorService.getAllReal());
        return "admins/colors";
    }
    @GetMapping("brand")
    public String getAllBrand(Model model){
        model.addAttribute("brands", brandService.getAllReal());
        return "admins/brands";
    }
    @GetMapping("/findByUserId/{userId}")
    public String findOrdersByUserId(@PathVariable int userId, Model model) {
        List<Orders> orders = orderService.findByUserId(userId);
        model.addAttribute("orders",orders);
        return "layoutUsers/gioHang";

    }
    @GetMapping( "/size")
    public String getAllSize(Model model) {
        model.addAttribute("sizes", sizeService.getAllSize());
        return "admins/size";
    }
    @GetMapping("xuatxu")
    public String getAllXuatXu(Model model){
        model.addAttribute("xuatxu",xuatXuService.getAllReal());
        return "admins/xuatxu";
    }
    @GetMapping("/product")
    public String getAll(
            @RequestParam(defaultValue = "0",name = "category_id")int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit
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
        return "admins/products";
    }
    @GetMapping("/addProduct")
    public String displayAddProduct(Model model){
        model.addAttribute("categories", categoryService.getAllReal());
        model.addAttribute("brands", brandService.getAllReal());
        model.addAttribute("xuatxu", xuatXuService.getAllReal());
        model.addAttribute("sizes", sizeService.getAllSize());
        model.addAttribute("colors", colorService.getAllReal());
        return "admins/addProduct";
    }
    @GetMapping("/categories")
    public String getAllCategories(Model model){
        model.addAttribute("categories",categoryService.getAllReal());

        return "categories";
    }

}
