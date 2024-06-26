package com.goorm.wordsketch.service;

import com.goorm.wordsketch.dto.AdminWordContent;
import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyType;
import com.goorm.wordsketch.repository.VocabularyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final VocabularyRepository vocabularyRepository;

    @Autowired
    public AdminService(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    public List<AdminWordContent> getAdminWordList() {
        List<Vocabulary> vocabularyList = vocabularyRepository.findAll();

        return vocabularyList.stream()
            .map(AdminWordContent::new)
            .toList();
    }

    public boolean registWord(AdminWordContent adminWordContent) {
        if (vocabularyRepository.existsBySubstance(adminWordContent.getVocabulary())) {
            return true;
        }

        Vocabulary vocabulary = Vocabulary.builder()
            .substance(adminWordContent.getVocabulary())
            .description(adminWordContent.getDescription())
            .type(VocabularyType.valueOf(adminWordContent.getType()))
            .build();

        vocabularyRepository.save(vocabulary);

        return false;
    }
}
