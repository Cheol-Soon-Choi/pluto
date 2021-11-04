package com.ccs.pluto.models.entity;

import com.ccs.pluto.models.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //상품명
    @Column(nullable = false, length = 50)
    private String itemName;

    //가격
    @Column(nullable = false)
    private int price;

    //재고
    @Column(nullable = false)
    private int stockNumber;

    //세부설명
    @Lob
    @Column(nullable = false)
    private String itemDetail;

    //상품 판매 상태
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Builder
    public Item(String itemName, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus) {
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
    }

}
