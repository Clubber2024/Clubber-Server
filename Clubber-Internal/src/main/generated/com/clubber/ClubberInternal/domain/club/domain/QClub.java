package com.clubber.ClubberInternal.domain.club.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClub is a Querydsl query type for Club
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClub extends EntityPathBase<Club> {

    private static final long serialVersionUID = -1938089192L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClub club = new QClub("club");

    public final com.clubber.ClubberInternal.global.common.QBaseEntity _super = new com.clubber.ClubberInternal.global.common.QBaseEntity(this);

    public final QClubInfo clubInfo;

    public final EnumPath<ClubType> clubType = createEnum("clubType", ClubType.class);

    public final EnumPath<College> college = createEnum("college", College.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<Department> department = createEnum("department", Department.class);

    public final EnumPath<Division> division = createEnum("division", Division.class);

    public final EnumPath<Hashtag> hashtag = createEnum("hashtag", Hashtag.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final BooleanPath isAgreeToProvideInfo = createBoolean("isAgreeToProvideInfo");

    public final BooleanPath isAgreeToReview = createBoolean("isAgreeToReview");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QClub(String variable) {
        this(Club.class, forVariable(variable), INITS);
    }

    public QClub(Path<? extends Club> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClub(PathMetadata metadata, PathInits inits) {
        this(Club.class, metadata, inits);
    }

    public QClub(Class<? extends Club> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubInfo = inits.isInitialized("clubInfo") ? new QClubInfo(forProperty("clubInfo")) : null;
    }

}

