package com.example.democuatao.controller.user;

import com.example.democuatao.Service.OrderDetailServiceImpl;
import com.example.democuatao.Service.OrderServiceImpl;
import com.example.democuatao.Service.ProductDetaiServiceimpl;
import com.example.democuatao.Service.UserServiceImpl;
import com.example.democuatao.dtos.OrderDTO;
import com.example.democuatao.dtos.OrderDetailDTO;
import com.example.democuatao.model.OrderDetails;
import com.example.democuatao.model.Orders;
import com.example.democuatao.model.User;
import com.example.democuatao.repositories.OrderRepo;
import com.example.democuatao.repositories.UserRepo;
import com.example.democuatao.responese.ProductResponse;
import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final OrderRepo orderRepo;
    private final UserServiceImpl userService;
    private final OrderServiceImpl orderService;
    private final OrderDetailServiceImpl orderDetailService;

    @GetMapping("/home")
    public String displayUser(Model model) {
        return "layoutUsers/home";
    }

    @GetMapping("/gioHang")
    public String disPlayGioHang() {
        return "layoutUsers/gioHang";
    }

    @GetMapping("/dssp")
    public String getAll(
            @RequestParam(defaultValue = "0", name = "category_id") int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit,
            Model model,
            HttpServletRequest request
    ) {
        Integer userId = getUserIdFromCookies(request.getCookies());

        PageRequest pageRequest;
        if (categoryId == 0) {
            pageRequest = PageRequest.of(page, limit);
        } else {
            pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());
        }
        Page<ProductResponse> productPage = productDetaiServiceimpl.getAllProduct(pageRequest);
        int totalPage = productPage.getTotalPages();
        List<ProductResponse> list = productPage.getContent();
        model.addAttribute("productList", list);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);

        if (userId != null) {
            List<Integer> orders = orderRepo.findOrderIdWhereUserIdAndStatus(userId);
            if (!orders.isEmpty()) {
                model.addAttribute("orderId", orders.get(0));
            }
        }

        return "layoutUsers/dssp";
    }


    private Integer getUserIdFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    try {
                        return Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        // Log error if needed
                        return null;
                    }
                }
            }
        }
        return null;
    }

    @PostMapping("login/go")
    @Deprecated
    public ModelAndView login(@RequestParam("username") String phoneNumber, @RequestParam("password") String password,
                              HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String token = userService.login(phoneNumber, password);

            session.removeAttribute("userId");

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
            System.out.println(token);

            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(24 * 60 * 60); // 1 ngày
            tokenCookie.setHttpOnly(false); // Đặt thành false nếu muốn truy cập từ JavaScript
            tokenCookie.setSecure(false); // Đặt thành true nếu trang của bạn sử dụng HTTPS
            response.addCookie(tokenCookie);

            // Thêm cookie lưu trữ số điện thoại
            Cookie phoneCookie = new Cookie("phoneNumber", phoneNumber);
            phoneCookie.setPath("/");
            phoneCookie.setMaxAge(24 * 60 * 60);
            phoneCookie.setHttpOnly(false);
            phoneCookie.setSecure(false);
            response.addCookie(phoneCookie);

            Optional<User> userOptional = userRepo.findByPhoneNumber(phoneNumber);
            User user = userOptional.get();

            session.setAttribute("userId", user.getId());
            Cookie userId = new Cookie("userId", String.valueOf(user.getId()));
            userId.setPath("/");
            userId.setMaxAge(24 * 60 * 60);
            userId.setHttpOnly(false);
            userId.setSecure(false);
            response.addCookie(userId);


            if (user.getRoleId().getId() == 1) {
                modelAndView.setViewName("redirect:/api/thuc/layout/color");
            } else {
                modelAndView.setViewName("redirect:/api/thuc/us/home");
            }

        } catch (Exception e) {
            modelAndView.setViewName("redirect:/login?error=invalid_credentials");
        }
        return modelAndView;
    }

    @GetMapping("logout")
    public String logout(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        session.removeAttribute("userId");

        return "redirect:/api/thuc/us/home";
    }

    @GetMapping("/findByUserId/{phoneNumber}")
    public String findOrdersByUserId(@PathVariable("phoneNumber") String phoneNumber, Model model) {
        List<Orders> orders = orderService.findByUserId(phoneNumber);
        model.addAttribute("orders", orders);
        return "layoutUsers/gioHang";

    }

    @GetMapping("/findByUserIdCheckOut/{phoneNumber}")
    public String findOrdersByUserIdCheckOut(@PathVariable String phoneNumber, Model model ,HttpServletRequest request) {
        List<Orders> orders = orderService.findByUserId(phoneNumber);
        Integer userIds = getUserIdFromCookies(request.getCookies());
        model.addAttribute("orders", orders);
        model.addAttribute("userId", userIds);
        model.addAttribute("orderDTO", new OrderDTO());
        return "layoutUsers/muaHang";

    }

    @PostMapping("/updateSoLuong")
    public String updateSoLuong(@RequestParam int id, @ModelAttribute @Valid OrderDetailDTO orderDetails, Model model) throws DataNotFound {
        orderDetailService.updateOrderNumber(id, orderDetails);
        return "redirect:/api/thuc/us/home";
    }

    @GetMapping("/cart")
    public String showCartPage(Model model, HttpSession session) {
        List<OrderDetails> orderDetails = (List<OrderDetails>) session.getAttribute("orderDetails");

        if (orderDetails == null || orderDetails.isEmpty()) {
            return "layoutUsers/gioHang";
        } else {
            model.addAttribute("orderDetails", orderDetails);

            return "layoutUsers/gioHang";
        }
    }

    @GetMapping("/cartCheckOut")
    public String showCartCheckOut(Model model, HttpSession session) {
        List<OrderDetails> orderDetails = (List<OrderDetails>) session.getAttribute("orderDetails");

        if (orderDetails == null || orderDetails.isEmpty()) {
            model.addAttribute("orderDTO", new OrderDTO());
        } else {
            model.addAttribute("orderDetails", orderDetails);
            model.addAttribute("orderDTO", new OrderDTO());
        }

        return "layoutUsers/muaHang";
    }


    @GetMapping("/orderComfirm")
    public String displayOrderComfirm() {
        return "layoutUsers/muaHang";
    }



}
