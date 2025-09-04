package com.clubber.ClubberInternal.domain.admin.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QContact is a Querydsl query type for Contact
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QContact extends BeanPath<Contact> {

    private static final long serialVersionUID = -1172747803L;

    public static final QContact contact = new QContact("contact");

    public final StringPath etc = createString("etc");

    public final StringPath instagram = createString("instagram");

    public QContact(String variable) {
        super(Contact.class, forVariable(variable));
    }

    public QContact(Path<? extends Contact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContact(PathMetadata metadata) {
        super(Contact.class, metadata);
    }

}

