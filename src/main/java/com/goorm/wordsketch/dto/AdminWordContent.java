package com.goorm.wordsketch.dto;

import com.goorm.wordsketch.entity.Vocabulary;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminWordContent {
    private String vocabulary;
    private String description;
    private String type;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;

    public AdminWordContent(Vocabulary vocabulary) {
        this.vocabulary = vocabulary.getSubstance();
        this.description = vocabulary.getDescription();
        this.type = vocabulary.getType().toString();
        this.createdDate = vocabulary.getCreatedDate();
        this.lastModifiedDate = vocabulary.getLastModifiedDate();
    }
}
