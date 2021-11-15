package com.ccs.pluto.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCart_item is a Querydsl query type for Cart_item
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCart_item extends EntityPathBase<Cart_item> {

    private static final long serialVersionUID = -1015093271L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCart_item cart_item = new QCart_item("cart_item");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCart cart;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QCart_item(String variable) {
        this(Cart_item.class, forVariable(variable), INITS);
    }

    public QCart_item(Path<? extends Cart_item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCart_item(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCart_item(PathMetadata metadata, PathInits inits) {
        this(Cart_item.class, metadata, inits);
    }

    public QCart_item(Class<? extends Cart_item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new QCart(forProperty("cart"), inits.get("cart")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item")) : null;
    }

}

