package com.ccs.pluto.models.entity;

import com.ccs.pluto.models.dto.ItemSearchDto;
import com.ccs.pluto.models.dto.MainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    //관리자 상품 검색
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    //메인 상품 검색
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
