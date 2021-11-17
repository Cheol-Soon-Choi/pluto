package com.ccs.pluto.service;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.constant.OrderStatus;
import com.ccs.pluto.models.dto.OrderDto;
import com.ccs.pluto.models.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    public Item createItem() {
        String itemName = "농구공";
        int price = 30000;
        int stockNumber = 11;
        String itemDetail = "더블 클러치 가능";

        return itemRepository.save(Item.builder()
                .itemName(itemName)
                .price(price)
                .stockNumber(stockNumber)
                .itemDetail(itemDetail)
                .itemSellStatus(ItemSellStatus.SELL)
                .build());
    }

    public Member createMember() {
        String name = "메시";
        String email = "Soccer@love.com";
        String address = "InMyHeart";
        String password = "1234";

        return memberRepository.save(Member.builder()
                .name(name)
                .email(email)
                .address(address)
                .password(password)
                .build());
    }

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        //given
        Item item = createItem();
        Member member = createMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        //when
        Long orderId = orderService.order(orderDto, member.getEmail());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        //then
        int totalPrice = orderDto.getCount() * item.getPrice();
        assertThat(totalPrice).isEqualTo(order.getTotalPrice());

    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder() {
        //given
        Item item = createItem();
        Member member = createMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        //when
        Long orderId = orderService.order(orderDto, member.getEmail());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId);

        //then
        assertThat(OrderStatus.CANCEL).isEqualTo(order.getOrderStatus());
        assertThat(11).isEqualTo(item.getStockNumber());
    }
}