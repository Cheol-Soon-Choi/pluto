package com.ccs.pluto.service;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.constant.OrderStatus;
import com.ccs.pluto.models.dto.CartItemDto;
import com.ccs.pluto.models.dto.CartOrderDto;
import com.ccs.pluto.models.dto.OrderDto;
import com.ccs.pluto.models.dto.OrderHistDto;
import com.ccs.pluto.models.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private ItemImgRepository itemImgRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    CartItemRepository cartItemRepository;

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

    @Test
    @DisplayName("주문 내역 조회")
    public void getOrderListTest() {
        //given
        Item item = createItem();
        Member member = createMember();
        Pageable pageable = PageRequest.of(0, 3);

        ItemImg itemImg1 = new ItemImg();
        itemImg1.setRepimgYn("Y");
        itemImg1.setItem(item);
        itemImg1.updateItemImg("a", "b", "c");
        itemImgRepository.save(itemImg1);

        OrderDto orderDto1 = new OrderDto();
        orderDto1.setCount(5);
        orderDto1.setItemId(item.getId());
        OrderDto orderDto2 = new OrderDto();
        orderDto2.setCount(3);
        orderDto2.setItemId(item.getId());

        //when
        Long orderId1 = orderService.order(orderDto1, member.getEmail());
        Long orderId2 = orderService.order(orderDto2, member.getEmail());
        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(member.getEmail(), pageable);

        //then
        assertThat(ordersHistDtoList.getTotalElements()).isEqualTo(2);
        assertThat(ordersHistDtoList.getTotalPages()).isEqualTo(1);
        int count1 = orderRepository.findById(orderId1).orElseThrow(EntityNotFoundException::new).getOrderItems().get(0).getCount();
        int count2 = orderRepository.findById(orderId2).orElseThrow(EntityNotFoundException::new).getOrderItems().get(0).getCount();
        assertThat(count1).isEqualTo(5);
        assertThat(count2).isEqualTo(3);
    }

    @Test
    @DisplayName("실제 주문자, db 주문자 일치 확인")
    public void validateOrderTest() {
        //given
        Item item = createItem();
        Member member = createMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        //when
        Long orderId = orderService.order(orderDto, member.getEmail());
        Boolean flag = orderService.validateOrder(orderId, member.getEmail()); // "Soccer@love.com";

        //then
        assertThat(flag).isTrue();
    }

    @Test
    @DisplayName("장바구니 주문")
    public void ordersTest() {
        //given
        Item item = createItem();
        Member member = createMember();

        CartItemDto cartItemDto1 = new CartItemDto();
        cartItemDto1.setItemId(item.getId());
        cartItemDto1.setCount(2);
        Long cartItemId = cartService.addCart(cartItemDto1, member.getEmail());

        List<CartOrderDto> cartOrderDtoList = new ArrayList<>();
        CartOrderDto cartOrderDto1 = new CartOrderDto();
        cartOrderDto1.setCartItemId(cartItemId);
        cartOrderDtoList.add(cartOrderDto1);

        CartItem cartItem = cartItemRepository.findById(cartOrderDtoList.get(0).getCartItemId()).orElseThrow(EntityNotFoundException::new);
        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(cartItem.getItem().getId());
        orderDto.setCount(cartItem.getCount());

        List<OrderDto> orderDtoList = new ArrayList<>();
        orderDtoList.add(orderDto);

        //when
        Long orderId = orderService.orders(orderDtoList, member.getEmail());

        //then
        OrderItem orderItem = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new).getOrderItems().get(0);
        assertThat(orderItem.getCount()).isEqualTo(2);
        assertThat(orderItem.getTotalPrice()).isEqualTo(60000); // 30000 * 2ea
    }
}