package com.example.democuatao.Service;

import com.example.democuatao.dtos.OrderDetailDTO;
import com.example.democuatao.model.OrderDetails;
import com.example.democuatao.model.Orders;
import com.example.democuatao.model.ProductDetails;
import com.example.democuatao.repositories.OrderRepo;
import com.example.democuatao.repositories.OrderdetailRepo;
import com.example.democuatao.repositories.ProductDetailRepo;
import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements IOrderDetailService{

    private final OrderdetailRepo orderdetailRepo;
    private final OrderRepo orderRepo;
    private final ProductDetailRepo productDetailRepo;

    @Override
    @Transactional
    public OrderDetails createOrder(OrderDetailDTO orderDetailDTO) throws DataNotFound {
        Orders order = orderRepo.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()-> new DataNotFound("khong tim thay voi id : "+orderDetailDTO.getOrderId()));
        ProductDetails product = productDetailRepo.findById(orderDetailDTO.getProductDetailId())
                .orElseThrow(()-> new DataNotFound("khong tim thay voi id : "+orderDetailDTO.getProductDetailId()));
        Optional<OrderDetails> existingOrderDetail = orderdetailRepo.findByOrdersAndProductDetails(order,product);
        if(existingOrderDetail.isPresent() && order.getStatus().equalsIgnoreCase("đang chơ xử lý")){
            OrderDetails orderDetailUpdate = existingOrderDetail.get();
            int newNumberOfProduct = orderDetailUpdate.getQuantifyProduct() +  orderDetailDTO.getNumberOfProduct();
            float newTotalMoney = orderDetailUpdate.getTotalMoney() + (orderDetailDTO.getTotalMoney() * orderDetailDTO.getNumberOfProduct()) ;
            orderDetailUpdate.setTotalMoney(newTotalMoney);
            orderDetailUpdate.setQuantifyProduct(newNumberOfProduct);
            return orderdetailRepo.save(orderDetailUpdate);
        }else{
            OrderDetails neworderDetails = OrderDetails.builder()
                    .orders(order)
                    .productDetails(product)
                    .price(orderDetailDTO.getPrice())
                    .totalMoney(orderDetailDTO.getTotalMoney())
                    .quantifyProduct(orderDetailDTO.getNumberOfProduct())
                    .build();
           return orderdetailRepo.save(neworderDetails);
        }


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
