package com.clubber.ClubberInternal.domain.admin.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPendingAdminInfo is a Querydsl query type for PendingAdminInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPendingAdminInfo extends EntityPathBase<PendingAdminInfo> {

    private static final long serialVersionUID = -956197727L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPendingAdminInfo pendingAdminInfo = new QPendingAdminInfo("pendingAdminInfo");

    public final com.clubber.ClubberInternal.global.common.QBaseEntity _super = new com.clubber.ClubberInternal.global.common.QBaseEntity(this);

    public final StringPath clubName = createString("clubName");

    public final EnumPath<com.clubber.ClubberInternal.domain.club.domain.ClubType> clubType = createEnum("clubType", com.clubber.ClubberInternal.domain.club.domain.ClubType.class);

    public final EnumPath<com.clubber.ClubberInternal.domain.club.domain.College> college = createEnum("college", com.clubber.ClubberInternal.domain.club.domain.College.class);

    public final QContact contact;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.clubber.ClubberInternal.domain.club.domain.Department> department = createEnum("department", com.clubber.ClubberInternal.domain.club.domain.Department.class);

    public final EnumPath<com.clubber.ClubberInternal.domain.club.domain.Division> division = createEnum("division", com.clubber.ClubberInternal.domain.club.domain.Division.class);

    public final StringPath email = createString("email");

    public final EnumPath<com.clubber.ClubberInternal.domain.club.domain.Hashtag> hashtag = createEnum("hashtag", com.clubber.ClubberInternal.domain.club.domain.Hashtag.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageForApproval = createString("imageForApproval");

    public final BooleanPath isApproved = createBoolean("isApproved");

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QPendingAdminInfo(String variable) {
        this(PendingAdminInfo.class, forVariable(variable), INITS);
    }

    public QPendingAdminInfo(Path<? extends PendingAdminInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPendingAdminInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPendingAdminInfo(PathMetadata metadata, PathInits inits) {
        this(PendingAdminInfo.class, metadata, inits);
    }

    public QPendingAdminInfo(Class<? extends PendingAdminInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contact = inits.isInitialized("contact") ? new QContact(forProperty("contact")) : null;
    }

}

