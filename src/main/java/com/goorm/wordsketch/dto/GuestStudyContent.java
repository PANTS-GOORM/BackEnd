package com.goorm.wordsketch.dto;

import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestStudyContent {

    private String vocabulary;

    private String description;

    private String contentURL;

    private String problemDescription;

    public GuestStudyContent(Vocabulary vocabulary, VocabularyContent vocabularyContent) {
        this.vocabulary = vocabulary.getSubstance();
        this.description = vocabulary.getDescription();
        this.contentURL = vocabularyContent.getContentURL();
        this.problemDescription = vocabularyContent.getProblemDescription();
    }
}
