package com.ccs.pluto.models.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.ccs.pluto.models.dto.QAdminItemDto is a Querydsl Projection type for AdminItemDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QAdminItemDto extends ConstructorExpression<AdminItemDto> {

    private static final long serialVersionUID = -897301810L;

    public QAdminItemDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemName, com.querydsl.core.types.Expression<com.ccs.pluto.models.constant.ItemSellStatus> itemSellStatus, com.querydsl.core.types.Expression<String> createdBy, com.querydsl.core.types.Expression<java.time.LocalDateTime> regTime) {
        super(AdminItemDto.class, new Class<?>[]{long.class, String.class, com.ccs.pluto.models.constant.ItemSellStatus.class, String.class, java.time.LocalDateTime.class}, id, itemName, itemSellStatus, createdBy, regTime);
    }

}

