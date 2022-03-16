package com.ccs.pluto.models.dto;

import com.ccs.pluto.models.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    //날짜 조건
    private String searchDateType;

    //판매 상태(품절 or 판매중)
    private ItemSellStatus searchSellStatus;

    //검색 조건(제품명 or 생성자)
    private String searchBy;

    //검색 내용
    private String searchQuery;

    @Builder
    public ItemSearchDto(String searchDateType, ItemSellStatus searchSellStatus, String searchBy, String searchQuery) {
        this.searchDateType = searchDateType;
        this.searchSellStatus = searchSellStatus;
        this.searchBy = searchBy;
        this.searchQuery = searchQuery;
    }
}