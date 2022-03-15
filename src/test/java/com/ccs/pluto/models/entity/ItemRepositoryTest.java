package com.ccs.pluto.models.entity;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    JPAQueryFactory queryFactory;

    @AfterEach
    public void cleanup() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 저장 테스트(itemRepository)")
    public void createItemTest() {
        //given
        String itemName = "축구공";
        int price = 10000;
        int stockNumber = 10;
        String itemDetail = "무회전 가능";

        itemRepository.save(Item.builder()
                .itemName(itemName)
                .price(price)
                .stockNumber(stockNumber)
                .itemDetail(itemDetail)
                .itemSellStatus(ItemSellStatus.SOLD_OUT)
                .build());

        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        Item items = itemList.get(0);
        assertThat(items.getItemName()).isEqualTo(itemName);
        assertThat(items.getPrice()).isEqualTo(price);
        assertThat(items.getStockNumber()).isEqualTo(stockNumber);
        assertThat(items.getItemDetail()).isEqualTo(itemDetail);
        assertThat(items.getItemSellStatus()).isEqualTo(ItemSellStatus.SOLD_OUT);
    }

    @Test
    @DisplayName("상품명 조회 테스트(findByItemName)")
    public void findByItemNmTest() {
        //given
        String itemName = "축구공";
        int price = 10000;
        int stockNumber = 10;
        String itemDetail = "무회전 가능";

        for (int i = 0; i < 3; i++) {
            itemRepository.save(Item.builder()
                    .itemName(itemName + i)
                    .price(price + i)
                    .stockNumber(stockNumber + i)
                    .itemDetail(itemDetail + i)
                    .itemSellStatus(ItemSellStatus.SOLD_OUT)
                    .build());
        }
        //when
        List<Item> itemList = itemRepository.findByItemName("축구공2");

        //then
        Item testItems = itemList.get(0);
        assertThat(testItems.getItemName()).isEqualTo("축구공2");
    }

    @Test
    @DisplayName("Query Test")
    public void queryTest() {
        //given
        String itemName = "축구공";
        int price = 10000;
        int stockNumber = 10;
        String itemDetail = "무회전 가능";

        for (int i = 0; i < 3; i++) {
            itemRepository.save(Item.builder()
                    .itemName(itemName + i)
                    .price(price + i)
                    .stockNumber(stockNumber + i)
                    .itemDetail(itemDetail + i)
                    .itemSellStatus(ItemSellStatus.SOLD_OUT)
                    .build());
        }
        //when
        List<Item> itemList = itemRepository.findByItemDetail("가능");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }

        //then
        Item testItems = itemList.get(0);
        assertThat(testItems.getItemDetail()).isEqualTo("무회전 가능2");
    }

    @Test
    @DisplayName("Querydsl Test1")
    public void querydslTest() {
        //given
        String itemName = "축구공";
        int price = 10000;
        int stockNumber = 10;
        String itemDetail = "무회전 가능";

        for (int i = 0; i < 3; i++) {
            itemRepository.save(Item.builder()
                    .itemName(itemName + i)
                    .price(price + i)
                    .stockNumber(stockNumber + i)
                    .itemDetail(itemDetail + i)
                    .itemSellStatus(ItemSellStatus.SOLD_OUT)
                    .build());
        }

        //when
        //Q클래스를 활용하여 쿼리를 동적으로 생성
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SOLD_OUT))
                .where(qItem.itemDetail.like("%" + "가능" + "%"))
                .orderBy(qItem.price.desc());
        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item.toString());
        }

        //then
        Item testItems = itemList.get(0);
        assertThat(testItems.getPrice()).isEqualTo(10002);
    }
}