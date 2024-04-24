package com.goorm.wordsketch.repository;

import com.goorm.wordsketch.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long>,
        QVocabularyRepository {

    boolean existsBySubstance(String substance);

    Optional<Vocabulary> findBySubstance(String substance);
}
