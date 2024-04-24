package com.goorm.wordsketch.repository;

import com.goorm.wordsketch.entity.SolvedVocabulary;
import com.goorm.wordsketch.entity.User;
import com.goorm.wordsketch.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolvedVocabularyRepository extends JpaRepository<SolvedVocabulary, Long> {
    List<SolvedVocabulary> findByUser(User user);

    Optional<SolvedVocabulary> findByUserAndVocabulary(User user, Vocabulary vocabulary);
}
