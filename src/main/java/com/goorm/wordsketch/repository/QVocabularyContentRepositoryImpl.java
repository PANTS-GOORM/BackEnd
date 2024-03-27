package com.goorm.wordsketch.repository;

import com.goorm.wordsketch.dto.GuestStudyContent;
import com.goorm.wordsketch.entity.QVocabularyContent;
import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyContent;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class QVocabularyContentRepositoryImpl extends QuerydslRepositorySupport implements QVocabularyContentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public QVocabularyContentRepositoryImpl(EntityManager entityManager) {
        super(VocabularyContent.class);
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<GuestStudyContent> findByVocabulary(List<Vocabulary> vocabularyList) {
        List<GuestStudyContent> result = new ArrayList<>();

        for (Vocabulary vocabulary : vocabularyList) {

            VocabularyContent vocabularyContent = jpaQueryFactory
                    .selectFrom(QVocabularyContent.vocabularyContent)
                    .where(QVocabularyContent.vocabularyContent.vocabulary.eq(vocabulary))
                    .orderBy(Expressions.stringTemplate("RANDOM()").asc())
                    .limit(1)
                    .fetchOne();

            if (vocabularyContent != null)
                result.add(new GuestStudyContent(vocabulary, vocabularyContent));

        }

        return result;
    }
}
