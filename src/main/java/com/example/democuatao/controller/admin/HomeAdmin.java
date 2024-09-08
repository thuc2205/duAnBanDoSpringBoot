package com.example.democuatao.controller.admin;


import com.example.democuatao.Service.BrandServiceImpl;
import com.example.democuatao.Service.CategoryServiceImpl;
import com.example.democuatao.Service.ColorServiceImpl;
import com.example.democuatao.Service.OrderServiceImpl;
import com.example.democuatao.Service.ProductDetaiServiceimpl;
import com.example.democuatao.Service.SizeServiceImpl;
import com.example.democuatao.Service.XuatXuServiceImpl;
import com.example.democuatao.model.Orders;
import com.example.democuatao.repositories.OrderRepo;
import com.example.democuatao.responese.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("${api.prefix}/layout")
@RequiredArgsConstructor
public class HomeAdmin {
    private final CategoryServiceImpl categoryService;
    private final ProductDetaiServiceimpl productDetaiServiceimpl;
    private final BrandServiceImpl brandService;
    private final ColorServiceImpl colorService;
    private final OrderServiceImpl orderService;
    private final OrderRepo orderRepo;
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
    public String getAllBrand(Model model

    ){

        model.addAttribute("brands", brandService.getAllReal());
        return "admins/brands";
    }
    @GetMapping("/donhang")
    public String xacNhanDonHang(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int limit
                                 ){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Orders> ordersPage = orderService.findByStatus(pageable);
        model.addAttribute("orders", ordersPage.getContent()); // Lấy danh sách đơn hàng từ Page
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        return "admins/xacNhan4DonHang";
    }
    @GetMapping("/donhangCb")
    public String xacNhanDonHangcb(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int limit
                                 ){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Orders> ordersPage = orderService.findByStatus2(pageable);
        model.addAttribute("orders", ordersPage.getContent()); // Lấy danh sách đơn hàng từ Page
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        return "admins/xacNhan4DonHang";
    }
    @GetMapping("/donhangDg")
    public String xacNhanDonHangDg(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int limit
                                 ){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Orders> ordersPage = orderService.findByStatus3(pageable);
        model.addAttribute("orders", ordersPage.getContent()); // Lấy danh sách đơn hàng từ Page
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        return "admins/xacNhan4DonHang";
    }
//    @GetMapping("getSoLuong/{idOrder}")
//    public Integer getSoLuong(@PathVariable("idOrder")Integer id){
//        return orderRepo.countByOrderDetails(id);
//    }



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
            @RequestParam(defaultValue = "10") int limit
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
    @GetMapping("/thong-ke")
    public String thongke(Model model) {
        Float tong = orderRepo.getTotalMoneyForSuccessfulOrdersToday();
        System.out.println(tong);
        model.addAttribute("tong", tong != null ? tong : 0);
        return "admins/thongke";
    }

    @GetMapping("/findOrder/{id}")
    public String findByOrder(Model model, @PathVariable Integer id) {
        Optional<Orders> orders = orderRepo.findById(id);
        if (orders.isPresent()) {
            Orders order = orders.get();
            model.addAttribute("orders", order);
            model.addAttribute("id", id);
            model.addAttribute("status", order.getStatus()); // Truyền trạng thái đơn hàng
        }
        return "admins/chiTietXacNhanDonHang";
    }


}
