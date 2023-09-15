package com.integrated.techhub.common.config;

import com.integrated.techhub.pr.infra.persist.PullRequestQueryRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QuerydslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public PullRequestQueryRepositoryImpl petFoodQueryRepository() {
        return new PullRequestQueryRepositoryImpl(jpaQueryFactory());
    }

}
