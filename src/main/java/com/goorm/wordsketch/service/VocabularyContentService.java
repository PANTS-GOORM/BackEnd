package com.goorm.wordsketch.service;

import com.goorm.wordsketch.repository.VocabularyContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VocabularyContentService {
    private final VocabularyContentRepository vocabularyContentRepository;

    @Autowired
    public VocabularyContentService(VocabularyContentRepository vocabularyContentRepository) {
        this.vocabularyContentRepository = vocabularyContentRepository;
    }

}
