package com.integrated.techhub.pr.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPullRequest is a Querydsl query type for PullRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPullRequest extends EntityPathBase<PullRequest> {

    private static final long serialVersionUID = 830714383L;

    public static final QPullRequest pullRequest = new QPullRequest("pullRequest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> missionId = createNumber("missionId", Long.class);

    public final EnumPath<com.integrated.techhub.pr.domain.type.Status> status = createEnum("status", com.integrated.techhub.pr.domain.type.Status.class);

    public final StringPath title = createString("title");

    public QPullRequest(String variable) {
        super(PullRequest.class, forVariable(variable));
    }

    public QPullRequest(Path<? extends PullRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPullRequest(PathMetadata metadata) {
        super(PullRequest.class, metadata);
    }

}

