package com.ccs.pluto.models.entity;

import com.ccs.pluto.exception.OutOfStockException;
import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.dto.ItemFormDto;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Setter
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //상품명
    @Column(nullable = false, length = 50)
    private String itemName;

    //가격
    @Column(name = "price", nullable = false)
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

    //상품 수정
    public void updateItem(ItemFormDto itemFormDto) {
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    //주문시 재고 감소
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException(this.itemName, this.stockNumber);
        }
        this.stockNumber = restStock;
    }

    //주문취소시 재고 증가
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}
