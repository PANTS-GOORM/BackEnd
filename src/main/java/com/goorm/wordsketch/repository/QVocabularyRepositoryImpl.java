package com.goorm.wordsketch.repository;

import com.goorm.wordsketch.entity.QVocabulary;
import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyType;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class QVocabularyRepositoryImpl extends QuerydslRepositorySupport implements QVocabularyRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public QVocabularyRepositoryImpl(EntityManager entityManager) {
        super(Vocabulary.class);
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Vocabulary> findByTypeAndAmount(VocabularyType type, int amount) {
        return jpaQueryFactory
                .selectFrom(QVocabulary.vocabulary)
                .where(QVocabulary.vocabulary.type.eq(type))
                .orderBy(Expressions.stringTemplate("RANDOM()").asc())
                .limit(amount)
                .fetch();
    }
}
