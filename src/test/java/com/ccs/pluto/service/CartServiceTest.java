package com.ccs.pluto.service;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.dto.CartItemDto;
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
class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    public Item createItem() {
        String itemName = "배구공";
        int price = 25000;
        int stockNumber = 10;
        String itemDetail = "식빵 가능";

        return itemRepository.save(Item.builder()
                .itemName(itemName)
                .price(price)
                .stockNumber(stockNumber)
                .itemDetail(itemDetail)
                .itemSellStatus(ItemSellStatus.SELL)
                .build());
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
        Item item = createItem();
        Member member = createMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        //when
        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        //then
        assertThat(item.getId()).isEqualTo(cartItem.getItem().getId());
        assertThat(cartItemDto.getCount()).isEqualTo(cartItem.getCount());
    }
}