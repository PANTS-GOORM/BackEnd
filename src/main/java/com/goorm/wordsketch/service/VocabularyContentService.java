package com.goorm.wordsketch.service;

import com.goorm.wordsketch.dto.GuestStudyContent;
import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.repository.VocabularyContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyContentService {
    private final VocabularyContentRepository vocabularyContentRepository;

    @Autowired
    public VocabularyContentService(VocabularyContentRepository vocabularyContentRepository) {
        this.vocabularyContentRepository = vocabularyContentRepository;
    }

    public List<GuestStudyContent> getGuestStudyContents(List<Vocabulary> vocabularyList) {
        return vocabularyContentRepository.findByVocabulary(vocabularyList);
    }
}
