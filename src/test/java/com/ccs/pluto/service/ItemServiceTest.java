package com.ccs.pluto.service;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.dto.AdminItemDto;
import com.ccs.pluto.models.dto.ItemFormDto;
import com.ccs.pluto.models.dto.ItemSearchDto;
import com.ccs.pluto.models.dto.MainItemDto;
import com.ccs.pluto.models.entity.Item;
import com.ccs.pluto.models.entity.ItemImg;
import com.ccs.pluto.models.entity.ItemImgRepository;
import com.ccs.pluto.models.entity.ItemRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    public void createItem() {
        String itemName = "축구공";
        String itemName2 = "농구공";
        int price = 10000;
        int stockNumber = 10;
        String itemDetail = "무회전 가능";

        for (int i = 0; i < 6; i++) {
            Item item = Item.builder()
                    .itemName(itemName + i)
                    .price(price + i)
                    .stockNumber(stockNumber + i)
                    .itemDetail(itemDetail + i)
                    .itemSellStatus(ItemSellStatus.SOLD_OUT)
                    .build();
            itemRepository.save(item);
            ItemImg itemImg = new ItemImg();
            itemImg.setRepimgYn("Y");
            itemImg.setItem(item);
            itemImg.updateItemImg("a", "b", "c");
            itemImgRepository.save(itemImg);

            Item item2 = Item.builder()
                    .itemName(itemName2 + i)
                    .price(price + i)
                    .stockNumber(stockNumber + i)
                    .itemDetail(itemDetail + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();
            itemRepository.save(item2);
            ItemImg itemImg2 = new ItemImg();
            itemImg2.setRepimgYn("N");
            itemImg2.setItem(item);
            itemImg2.updateItemImg("e", "f", "g");
            itemImgRepository.save(itemImg2);
        }
    }

    @Test
    @Order(1)
    @DisplayName("상품 세부정보 가져오기 테스트")
    public void getItemDtlTest() {
        //given
        createItem();

        //when
        Long id = itemRepository.findAll().get(0).getId();
        ItemFormDto itemFormDto = itemService.getItemDtl(id);

        //then
        assertThat(itemFormDto.getItemName()).isEqualTo("축구공0");
        assertThat(itemFormDto.getItemSellStatus()).isEqualTo(ItemSellStatus.SOLD_OUT);
        assertThat(itemFormDto.getItemImgDtoList().get(0).getImgName()).isEqualTo("b");
    }

    @Test
    @Order(2)
    @DisplayName("관리자 상품 검색 및 페이지 출력 테스트")
    public void getAdminItemPageTest() {
        //given
        Pageable pageable = PageRequest.of(0, 3);
        ItemSearchDto itemSearchDto = ItemSearchDto.builder()
                .searchSellStatus(ItemSellStatus.SELL)
                .searchDateType("1d")
                .searchQuery("")
                .searchBy("itemName")
                .build();

        createItem();

        //when
        Page<AdminItemDto> items = itemRepository.getAdminItemPage(itemSearchDto, pageable);

        //then
        assertThat(items.getTotalElements()).isEqualTo(6); // 총 데이터 개수
        assertThat(items.getTotalPages()).isEqualTo(2); // 총 페이지수
        for (int i = 0; i < 3; i++) {
            assertThat(items.getContent().get(i).getItemName()).contains("농구");
        } // 첫 페이지 농구공만 출력
    }

    @Test
    @Order(3)
    @DisplayName("메인 상품 검색 및 페이지 출력 테스트")
    public void getMainItemPageTest() {
        //given
        Pageable pageable = PageRequest.of(0, 6);
        ItemSearchDto itemSearchDto = ItemSearchDto.builder()
                .searchQuery("")
                .build();

        createItem();

        //when
        Page<MainItemDto> items = itemRepository.getMainItemPage(itemSearchDto, pageable);

        //then
        assertThat(items.getTotalElements()).isEqualTo(6); // 총 데이터 개수
        assertThat(items.getTotalPages()).isEqualTo(1); // 총 페이지수
    }
}