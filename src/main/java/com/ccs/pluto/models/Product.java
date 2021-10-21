package com.ccs.pluto.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Product extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="product_id" , nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String desc;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int star = 0;

    @Column(nullable = false)
    private int code;

    public Product(ProductRequestDto productRequestDto){
        this.name = productRequestDto.getName();
        this.price = productRequestDto.getPrice();
        this.desc = productRequestDto.getDesc();
        this.image = productRequestDto.getImage();
        this.quantity = productRequestDto.getQuantity();
        this.code = productRequestDto.getCode();
    }

    public Product(String name, int price, String desc, String image, int quantity, int code) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.image = image;
        this.quantity = quantity;
        this.code = code;
    }

    public void update(ProductRequestDto productRequestDto){
        this.name = productRequestDto.getName();
        this.price = productRequestDto.getPrice();
        this.desc = productRequestDto.getDesc();
        this.image = productRequestDto.getImage();
        this.quantity = productRequestDto.getQuantity();
        this.code = productRequestDto.getCode();
    }
}
