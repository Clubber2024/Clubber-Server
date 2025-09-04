package com.clubber.ClubberInternal.domain.internal.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInternalAdmin is a Querydsl query type for InternalAdmin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInternalAdmin extends EntityPathBase<InternalAdmin> {

    private static final long serialVersionUID = -1564568887L;

    public static final QInternalAdmin internalAdmin = new QInternalAdmin("internalAdmin");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final StringPath username = createString("username");

    public QInternalAdmin(String variable) {
        super(InternalAdmin.class, forVariable(variable));
    }

    public QInternalAdmin(Path<? extends InternalAdmin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInternalAdmin(PathMetadata metadata) {
        super(InternalAdmin.class, metadata);
    }

}

