package com.clubber.ClubberInternal.domain.admin.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdmin is a Querydsl query type for Admin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdmin extends EntityPathBase<Admin> {

    private static final long serialVersionUID = -1907304876L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdmin admin = new QAdmin("admin");

    public final com.clubber.ClubberInternal.global.common.QBaseEntity _super = new com.clubber.ClubberInternal.global.common.QBaseEntity(this);

    public final EnumPath<com.clubber.ClubberInternal.global.jwt.AccountRole> accountRole = createEnum("accountRole", com.clubber.ClubberInternal.global.jwt.AccountRole.class);

    public final EnumPath<AccountState> accountState = createEnum("accountState", AccountState.class);

    public final com.clubber.ClubberInternal.domain.club.domain.QClub club;

    public final QContact contact;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QAdmin(String variable) {
        this(Admin.class, forVariable(variable), INITS);
    }

    public QAdmin(Path<? extends Admin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdmin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdmin(PathMetadata metadata, PathInits inits) {
        this(Admin.class, metadata, inits);
    }

    public QAdmin(Class<? extends Admin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new com.clubber.ClubberInternal.domain.club.domain.QClub(forProperty("club"), inits.get("club")) : null;
        this.contact = inits.isInitialized("contact") ? new QContact(forProperty("contact")) : null;
    }

}

