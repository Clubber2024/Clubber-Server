package com.clubber.ClubberInternal.domain.club.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubInfo is a Querydsl query type for ClubInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubInfo extends EntityPathBase<ClubInfo> {

    private static final long serialVersionUID = 1424664550L;

    public static final QClubInfo clubInfo = new QClubInfo("clubInfo");

    public final com.clubber.ClubberInternal.global.common.QBaseEntity _super = new com.clubber.ClubberInternal.global.common.QBaseEntity(this);

    public final StringPath activity = createString("activity");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath instagram = createString("instagram");

    public final StringPath leader = createString("leader");

    public final NumberPath<Long> room = createNumber("room", Long.class);

    public final NumberPath<Long> totalView = createNumber("totalView", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath youtube = createString("youtube");

    public QClubInfo(String variable) {
        super(ClubInfo.class, forVariable(variable));
    }

    public QClubInfo(Path<? extends ClubInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubInfo(PathMetadata metadata) {
        super(ClubInfo.class, metadata);
    }

}

