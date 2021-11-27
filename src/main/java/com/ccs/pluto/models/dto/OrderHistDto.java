package com.ccs.pluto.models.dto;

import com.ccs.pluto.models.constant.OrderStatus;
import com.ccs.pluto.models.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    //주문아이디
    private Long orderId;

    //주문날짜
    private String orderDate;

    //주문 상태
    private OrderStatus orderStatus;

    //주문 리스트
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    //주문 총 금액
    private int totalPrice;

    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
        this.totalPrice = order.getTotalPrice();
    }

    //주문 상품리스트
    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }

}
