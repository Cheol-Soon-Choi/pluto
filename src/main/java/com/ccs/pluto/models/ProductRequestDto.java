package com.ccs.pluto.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ProductRequestDto {

    private final String name;
    private final int price;
    private final String desc;
    private final String image;
    private final int quantity;
    private final int code;

}
