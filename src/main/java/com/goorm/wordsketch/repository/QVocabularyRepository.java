package com.goorm.wordsketch.repository;

import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyType;

import java.util.List;

public interface QVocabularyRepository {
    List<Vocabulary> findByTypeAndAmount(VocabularyType type, int amount);
}
