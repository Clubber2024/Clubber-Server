package com.clubber.ClubberInternal.global.vo.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImageVO is a Querydsl query type for ImageVO
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QImageVO extends BeanPath<ImageVO> {

    private static final long serialVersionUID = 578090173L;

    public static final QImageVO imageVO = new QImageVO("imageVO");

    public final StringPath imageUrl = createString("imageUrl");

    public QImageVO(String variable) {
        super(ImageVO.class, forVariable(variable));
    }

    public QImageVO(Path<? extends ImageVO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImageVO(PathMetadata metadata) {
        super(ImageVO.class, metadata);
    }

}

