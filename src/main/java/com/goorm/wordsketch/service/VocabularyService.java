package com.goorm.wordsketch.service;

import com.goorm.wordsketch.dto.GuestStudyContent;
import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyType;
import com.goorm.wordsketch.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private final VocabularyContentService vocabularyContentService;

    @Autowired
    public VocabularyService(VocabularyRepository vocabularyRepository, VocabularyContentService vocabularyContentService) {
        this.vocabularyRepository = vocabularyRepository;
        this.vocabularyContentService = vocabularyContentService;
    }

    public List<GuestStudyContent> getGuestProblem(VocabularyType type, Integer amount) {
        List<Vocabulary> vocabularyList = vocabularyRepository.findByTypeAndAmount(type, amount);
        return vocabularyContentService.getGuestStudyContents(vocabularyList);
    }
}
