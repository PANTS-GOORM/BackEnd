package com.goorm.wordsketch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goorm.wordsketch.entity.VocabularyContent;

@Repository
public interface VocabularyContentRepository extends JpaRepository<VocabularyContent, Long> {
}
