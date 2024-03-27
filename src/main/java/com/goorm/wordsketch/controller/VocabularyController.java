package com.goorm.wordsketch.controller;

import com.goorm.wordsketch.dto.GuestStudyContent;
import com.goorm.wordsketch.entity.VocabularyType;
import com.goorm.wordsketch.service.VocabularyService;
import com.goorm.wordsketch.util.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/vocab")
public class VocabularyController {
    private final VocabularyService vocabularyService;
    private final ErrorHandler errorHandler;

    @Autowired
    public VocabularyController(VocabularyService vocabularyService, ErrorHandler errorHandler) {
        this.vocabularyService = vocabularyService;
        this.errorHandler = errorHandler;
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomContentList(@RequestParam("type") VocabularyType type, @RequestParam("amount") Integer amount) {
        try {
            List<GuestStudyContent> result = vocabularyService.getGuestProblem(type, amount);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return errorHandler.errorMessage(e);
        }
    }

}
