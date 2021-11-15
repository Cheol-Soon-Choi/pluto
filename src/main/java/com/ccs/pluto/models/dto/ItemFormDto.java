package com.ccs.pluto.models.dto;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private int stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    //ItemFormDto -> Item 객체 전환
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    //Item -> ItemFormDto 객체 전환
    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }

}