package com.example.democuatao.controller.user;

import com.example.democuatao.Service.ProductDetaiServiceimpl;
import com.example.democuatao.Service.UserServiceImpl;
import com.example.democuatao.model.User;
import com.example.democuatao.repositories.UserRepo;
import com.example.democuatao.responese.ProductResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/us")
public class HomeController {

    private final ProductDetaiServiceimpl productDetaiServiceimpl;
    private final UserRepo userRepo;
    private final UserServiceImpl userService;

    @GetMapping("/home")
    public String displayUser(Model model){
        return "layoutUsers/home";
    }
    @GetMapping("/gioHang")
    public String disPlayGioHang(){
        return "layoutUsers/gioHang";
    }

    @GetMapping("dssp")
    public String getAll(
            @RequestParam(defaultValue = "0",name = "category_id")int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit
            , Model model
            ,HttpSession session
    ){
        session.getAttribute("user");
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

    @PostMapping("login/go")
    @Deprecated
    public ModelAndView login(@RequestParam("username") String phoneNumber, @RequestParam("password") String password,
                        HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String token = userService.login(phoneNumber, password);

            // Xóa tất cả các cookie cũ trước khi thiết lập cookie mới
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setValue("");
                    cookie.setPath("/"); // Đảm bảo đường dẫn giống với cookie ban đầu
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }

            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(24 * 60 * 60); // 1 ngày
            tokenCookie.setHttpOnly(false); // Đặt thành false nếu muốn truy cập từ JavaScript
            tokenCookie.setSecure(false); // Đặt thành true nếu trang của bạn sử dụng HTTPS
            response.addCookie(tokenCookie);

            // Thêm cookie lưu trữ số điện thoại
            Cookie phoneCookie = new Cookie("phoneNumber", phoneNumber);
            phoneCookie.setPath("/");
            phoneCookie.setMaxAge(24 * 60 * 60); // 1 ngày
            phoneCookie.setHttpOnly(false);
            phoneCookie.setSecure(false);
            response.addCookie(phoneCookie);

            Optional<User> userOptional = userRepo.findByPhoneNumber(phoneNumber);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                if (user.getRoleId().getId() == 1) {
                    modelAndView.setViewName("redirect:/api/thuc/layout/color");
                } else {
                    session.setAttribute("user", user);
                    modelAndView.setViewName("redirect:/api/thuc/us/home");
                }
            } else {
                modelAndView.setViewName("redirect:/login?error=user_not_found");
            }

        } catch (Exception e) {
            modelAndView.setViewName("redirect:/login?error=invalid_credentials");
        }
        return modelAndView;
    }
    @GetMapping("logout")
    public String logout(HttpServletResponse response ,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/api/thuc/us/home";
    }

}
