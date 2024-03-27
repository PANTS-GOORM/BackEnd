package com.goorm.wordsketch.repository;

import com.goorm.wordsketch.dto.GuestStudyContent;
import com.goorm.wordsketch.entity.Vocabulary;

import java.util.List;

public interface QVocabularyContentRepository {

    List<GuestStudyContent> findByVocabulary(List<Vocabulary> vocabularyList);
}
