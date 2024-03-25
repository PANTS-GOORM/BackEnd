package com.goorm.wordsketch.controller;

import com.goorm.wordsketch.service.VocabularyService;
import com.goorm.wordsketch.util.ErrorHandler;
import org.hibernate.dialect.function.CastFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/vocabulary")
public class VocabularyController {
    private final VocabularyService vocabularyService;
    private final ErrorHandler errorHandler;

    @Autowired
    public VocabularyController(VocabularyService vocabularyService, ErrorHandler errorHandler) {
        this.vocabularyService = vocabularyService;
        this.errorHandler = errorHandler;
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomContentList() {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return errorHandler.errorMessage(e);
        }
    }

}
