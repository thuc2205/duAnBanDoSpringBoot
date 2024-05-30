package com.example.democuatao.Service;

import com.example.democuatao.dtos.OrderDetailDTO;
import com.example.democuatao.model.OrderDetails;
import com.example.democuatao.model.Orders;
import com.example.democuatao.model.ProductDetails;
import com.example.democuatao.model.User;
import com.example.democuatao.repositories.OrderRepo;
import com.example.democuatao.repositories.OrderdetailRepo;
import com.example.democuatao.repositories.ProductDetailRepo;
import com.example.democuatao.repositories.UserRepo;
import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService{

    private final OrderdetailRepo orderdetailRepo;
    private final OrderRepo orderRepo;
    private final ProductDetailRepo productDetailRepo;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public OrderDetails createOrder(OrderDetailDTO orderDetailDTO, HttpServletRequest request) throws DataNotFound {

        Integer userId = getUserIdFromCookies(request.getCookies());
        Optional<User> user = Optional.empty();

        // Kiểm tra người dùng có tồn tại không
        if (userId != null) {
            user = userRepo.findById(userId);
            if (user.isEmpty()) {
                throw new DataNotFound("Không tìm thấy người dùng với ID: " + userId);
            }
        }

        Orders order = orderRepo.findById(orderDetailDTO.getOrderId()).orElse(null);
        ProductDetails product = productDetailRepo.findById(orderDetailDTO.getProductDetailId())
                .orElseThrow(() -> new DataNotFound("Không tìm thấy với ID sản phẩm: " + orderDetailDTO.getProductDetailId()));

        // Người dùng chưa đăng nhập, lưu vào session
        if (userId == null) {
            OrderDetails newOrderDetails = OrderDetails.builder()
                    .productDetails(product)
                    .price(orderDetailDTO.getPrice())
                    .totalMoney(orderDetailDTO.getPrice())
                    .quantifyProduct(1)
                    .build();
            List<OrderDetails> orderDetails = (List<OrderDetails>) request.getSession().getAttribute("orderDetails");
            if (orderDetails == null) {
                orderDetails = new ArrayList<>();
                request.getSession().setAttribute("orderDetails", orderDetails);
            }
            for (OrderDetails orderDetail : orderDetails) {
                if (orderDetail.getProductDetails().getId() == (orderDetailDTO.getProductDetailId())) {
                    int newNumberOfProduct = orderDetail.getQuantifyProduct() + orderDetailDTO.getNumberOfProduct();
                    float newTotalMoney = orderDetail.getTotalMoney() + product.getPrice() * orderDetailDTO.getNumberOfProduct();
                    orderDetail.setTotalMoney(newTotalMoney);
                    orderDetail.setQuantifyProduct(newNumberOfProduct);
                    return orderDetail;
                }
            }

            orderDetails.add(newOrderDetails);
            return newOrderDetails;

    }

        // Tạo đơn hàng mới nếu chưa tồn tại
        if (order == null) {
            Orders newOrders = Orders.builder()
                    .user(user.orElse(null))
                    .phoneNumber("0966719465")
                    .status("đang chờ xử lý")
                    .orderDate(new Date())
                    .active(true)
                    .build();
            orderRepo.save(newOrders);
            order = newOrders;
        }

        // Kiểm tra xem đơn hàng chi tiết đã tồn tại hay không
        Optional<OrderDetails> existingOrderDetail = orderdetailRepo.findByOrdersAndProductDetails(order, product);
        if (existingOrderDetail.isPresent() && order.getStatus().equalsIgnoreCase("đang chờ xử lý")) {
            // Cập nhật thông tin nếu đã tồn tại
            OrderDetails orderDetailUpdate = existingOrderDetail.get();
            int newNumberOfProduct = orderDetailUpdate.getQuantifyProduct() + orderDetailDTO.getNumberOfProduct();
            float newTotalMoney = orderDetailUpdate.getTotalMoney() + product.getPrice() * orderDetailDTO.getNumberOfProduct();
            orderDetailUpdate.setTotalMoney(newTotalMoney);
            orderDetailUpdate.setQuantifyProduct(newNumberOfProduct);
            return orderdetailRepo.save(orderDetailUpdate);
        } else {
            // Tạo mới đơn hàng chi tiết
            OrderDetails newOrderDetails = OrderDetails.builder()
                    .orders(order)
                    .productDetails(product)
                    .price(product.getPrice())
                    .totalMoney(product.getPrice() * orderDetailDTO.getNumberOfProduct())
                    .quantifyProduct(orderDetailDTO.getNumberOfProduct())
                    .build();
            return orderdetailRepo.save(newOrderDetails);
        }
    }


    private Integer getUserIdFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    try {
                        return Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }


    @Transactional
    public OrderDetails updateOrderNumber(int id,OrderDetailDTO orderDetailDTO) throws DataNotFound {
        OrderDetails orderDetails = getOrderById(id);
        orderDetails.setQuantifyProduct(orderDetailDTO.getNumberOfProduct());
        float total = orderDetails.getPrice() * orderDetails.getQuantifyProduct();
        orderDetails.setTotalMoney(total);
        return orderdetailRepo.save(orderDetails);
    }

    @Override
    public OrderDetails getOrderById(int id) {
        return null;
    }

    @Override
    public OrderDetails updateOrder(int id, OrderDetailDTO orderDetailDTO) {
        return null;
    }

    @Override
    public void deleteOrder(int id) {

    }
}
