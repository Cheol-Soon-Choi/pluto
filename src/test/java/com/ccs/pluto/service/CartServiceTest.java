package com.ccs.pluto.service;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.dto.CartDetailDto;
import com.ccs.pluto.models.dto.CartItemDto;
import com.ccs.pluto.models.dto.CartOrderDto;
import com.ccs.pluto.models.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    @Autowired
    OrderRepository orderRepository;

    public void createItem() {
        Item item1 = itemRepository.save(Item.builder()
                .itemName("배구공")
                .price(30000)
                .stockNumber(10)
                .itemDetail("2022 New Nike")
                .itemSellStatus(ItemSellStatus.SELL)
                .build());
        ItemImg itemImg1 = new ItemImg();
        itemImg1.setRepimgYn("Y");
        itemImg1.setItem(item1);
        itemImg1.updateItemImg("a", "b", "c");
        itemImgRepository.save(itemImg1);

        Item item2 = itemRepository.save(Item.builder()
                .itemName("축구공")
                .price(60000)
                .stockNumber(5)
                .itemDetail("2022 New Adidas")
                .itemSellStatus(ItemSellStatus.SELL)
                .build());
        ItemImg itemImg2 = new ItemImg();
        itemImg2.setRepimgYn("Y");
        itemImg2.setItem(item2);
        itemImg2.updateItemImg("a", "b", "c");
        itemImgRepository.save(itemImg2);
    }

    public Member createMember() {
        String name = "김연경";
        String email = "volleyball@love.com";
        String address = "New york";
        String password = "1234";

        return memberRepository.save(Member.builder()
                .name(name)
                .email(email)
                .address(address)
                .password(password)
                .build());
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart() {
        //given
        createItem();
        Member member = createMember();
        List<Item> itemList = itemRepository.findAll();

        CartItemDto cartItemDto1 = new CartItemDto();
        cartItemDto1.setItemId(itemList.get(0).getId());
        cartItemDto1.setCount(1);
        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setItemId(itemList.get(1).getId());
        cartItemDto2.setCount(5);
        CartItemDto cartItemDto3 = new CartItemDto();
        cartItemDto3.setItemId(itemList.get(0).getId());
        cartItemDto3.setCount(2);

        //when
        Long cartItemId1 = cartService.addCart(cartItemDto1, member.getEmail()); // 배구공 1개 담기
        Long cartItemId2 = cartService.addCart(cartItemDto2, member.getEmail()); // 축구공 5개 담기
        Long cartItemId3 = cartService.addCart(cartItemDto3, member.getEmail()); // 배구공 2개 담기

        //then
        CartItem cartItem1 = cartItemRepository.findById(cartItemId1).orElseThrow(EntityNotFoundException::new);
        CartItem cartItem2 = cartItemRepository.findById(cartItemId2).orElseThrow(EntityNotFoundException::new);

        assertThat(itemList.get(0).getId()).isEqualTo(cartItem1.getItem().getId());
        assertThat(3).isEqualTo(cartItem1.getCount()); // 배구공 총 3개
        assertThat(itemList.get(1).getId()).isEqualTo(cartItem2.getItem().getId());
        assertThat(cartItemDto2.getCount()).isEqualTo(cartItem2.getCount());
    }

    @Test
    @DisplayName("장바구니 목록 조회 테스트")
    public void getCartList() {
        //given
        createItem();
        Member member = createMember();
        List<Item> itemList = itemRepository.findAll();

        CartItemDto cartItemDto1 = new CartItemDto();
        cartItemDto1.setItemId(itemList.get(0).getId());
        cartItemDto1.setCount(1);
        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setItemId(itemList.get(1).getId());
        cartItemDto2.setCount(5);

        cartService.addCart(cartItemDto1, member.getEmail());
        cartService.addCart(cartItemDto2, member.getEmail());

        //when
        List<CartDetailDto> cartDetailDtoList = cartService.getCartList(member.getEmail());

        //then
        assertThat(2).isEqualTo(cartDetailDtoList.size());
        assertThat(cartDetailDtoList.get(0).getItemName()).isEqualTo("축구공");
        assertThat(cartDetailDtoList.get(1).getItemName()).isEqualTo("배구공");
    }

    @Test
    @DisplayName("장바구니 수량 업데이트")
    public void updateCartItemCount() {
        //given
        createItem();
        Member member = createMember();
        List<Item> itemList = itemRepository.findAll();

        CartItemDto cartItemDto1 = new CartItemDto();
        cartItemDto1.setItemId(itemList.get(0).getId());
        cartItemDto1.setCount(1);
        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setItemId(itemList.get(1).getId());
        cartItemDto2.setCount(5);

        Long cartItemId1 = cartService.addCart(cartItemDto1, member.getEmail());
        Long cartItemId2 = cartService.addCart(cartItemDto2, member.getEmail());

        //when
        cartService.updateCartItemCount(cartItemId1, 3); // 배구공 수량(1 -> 3)
        cartService.updateCartItemCount(cartItemId2, 2); // 축구공 수량 변경(5 -> 2)

        //then
        int count1 = cartItemRepository.findById(cartItemId1).orElseThrow(EntityNotFoundException::new).getCount();
        int count2 = cartItemRepository.findById(cartItemId2).orElseThrow(EntityNotFoundException::new).getCount();
        assertThat(count1).isEqualTo(3);
        assertThat(count2).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 삭제")
    public void deleteCartItem() {
        //given
        createItem();
        Member member = createMember();
        List<Item> itemList = itemRepository.findAll();

        CartItemDto cartItemDto1 = new CartItemDto();
        cartItemDto1.setItemId(itemList.get(0).getId());
        cartItemDto1.setCount(1);
        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setItemId(itemList.get(1).getId());
        cartItemDto2.setCount(5);

        Long cartItemId1 = cartService.addCart(cartItemDto1, member.getEmail());
        Long cartItemId2 = cartService.addCart(cartItemDto2, member.getEmail());

        //when
        cartService.deleteCartItem(cartItemId1); // 배구공 삭제
        cartService.deleteCartItem(cartItemId2); // 축구공 삭제

        //then
        assertThat(cartItemRepository.findAll()).isEmpty(); // 장바구니 해당 아이템 삭제 확인
    }

    @Test
    @DisplayName("장바구니 주문 테스트")
    public void orderCartItem() {
        //given
        createItem();
        Member member = createMember();
        List<Item> itemList = itemRepository.findAll();

        CartItemDto cartItemDto1 = new CartItemDto();
        cartItemDto1.setItemId(itemList.get(0).getId());
        cartItemDto1.setCount(1);
        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setItemId(itemList.get(1).getId());
        cartItemDto2.setCount(5);

        Long cartItemId1 = cartService.addCart(cartItemDto1, member.getEmail());
        Long cartItemId2 = cartService.addCart(cartItemDto2, member.getEmail());

        List<CartOrderDto> cartOrderDtoList = new ArrayList<>();
        CartOrderDto cartOrderDto1 = new CartOrderDto();
        cartOrderDto1.setCartItemId(cartItemId1);
        cartOrderDtoList.add(cartOrderDto1);
        CartOrderDto cartOrderDto2 = new CartOrderDto();
        cartOrderDto2.setCartItemId(cartItemId2);
        cartOrderDtoList.add(cartOrderDto2);

        //when
        Long orderId = cartService.orderCartItem(cartOrderDtoList, member.getEmail());

        //then
        OrderItem orderItem1 = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new).getOrderItems().get(0);
        OrderItem orderItem2 = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new).getOrderItems().get(1);

        assertThat(orderItem1.getTotalPrice()).isEqualTo(30000); // 배구공 30000 * 1ea
        assertThat(orderItem2.getTotalPrice()).isEqualTo(60000 * 5); // 축구공 60000 * 5ea
        assertThat(cartItemRepository.findAll()).isEmpty(); // 장바구니 주문 후 해당 아이템 삭제 확인
    }
}