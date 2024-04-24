package com.goorm.wordsketch.controller;

import com.goorm.wordsketch.dto.UserSolvedVocabulary;
import com.goorm.wordsketch.entity.SolvedVocabulary;
import com.goorm.wordsketch.entity.User;
import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyType;
import com.goorm.wordsketch.service.SolvedVocabularyService;
import com.goorm.wordsketch.util.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/solved")
public class SolvedVocabularyController {
    private final SolvedVocabularyService solvedVocabularyService;
    private final ErrorHandler errorHandler;

    @Autowired
    public SolvedVocabularyController(SolvedVocabularyService solvedVocabularyService, ErrorHandler errorHandler) {
        this.solvedVocabularyService = solvedVocabularyService;
        this.errorHandler = errorHandler;
    }

    /*
     * 유저가 풀었던 문제리스트 반환
     * @param userEmail 유저 이메일(유저 식별용)
     * @return ReponseEntity<>() 유저가 풀었던 문제리스트, OK(200)
     * */
    @GetMapping("/vocabularylist")
    public ResponseEntity<?> vocabularyList(@RequestParam String userEmail) {
        try {
            Optional<User> user = solvedVocabularyService.getUser(userEmail);
            List<UserSolvedVocabulary> solvedVocabularyList = solvedVocabularyService.getUserSolvedVocabulary(user.get());

            // 여기에 테스트용 더미 데이터 생성
            List<SolvedVocabulary> dummyData = new ArrayList<>();

            // 더미 데이터 추가
            dummyData.add(
                    SolvedVocabulary.builder()
                            .id(1L)
                            .user(user.get())
                            .vocabulary(new Vocabulary(1L, "Apple", "A fruit that is red or green.", VocabularyType.사자성어, null, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())))
                            .createdDate(new Timestamp(System.currentTimeMillis()))
                            .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                            .build()
            );

            // 더미 데이터 추가
            dummyData.add(
                    SolvedVocabulary.builder()
                            .id(2L)
                            .user(user.get())
                            .vocabulary(new Vocabulary(2L, "Computer", "An electronic device for storing and processing data.", VocabularyType.단어, null, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())))
                            .createdDate(new Timestamp(System.currentTimeMillis()))
                            .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                            .build()
            );

            List<UserSolvedVocabulary> res = new ArrayList<>();
            for (SolvedVocabulary sv : dummyData) {
                UserSolvedVocabulary userSolvedVocabulary = new UserSolvedVocabulary(sv.getVocabulary().getSubstance()
                        , sv.getVocabulary().getDescription()
                        , sv.getCreatedDate().toLocalDateTime().getMonthValue());
                res.add(userSolvedVocabulary);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
//            return new ResponseEntity<>(solvedVocabularyList, HttpStatus.OK);
        } catch (Exception e) {
            return errorHandler.errorMessage(e);
        }
    }

    /*
     * 유저가 푼 문제 등록
     * @param userEmail 유저 이메일(유저 식별용)
     * @param substance 문제명(문제 식별용)
     * @return ReponseEntity<>() 유저가 푼 문제, OK(200)
     * */
    @PostMapping("/vocabularyregist")
    public ResponseEntity<?> registVocabulary(@RequestParam String userEmail, @RequestParam String substance) {
        try {
            Optional<User> user = solvedVocabularyService.getUser(userEmail);
            Optional<Vocabulary> vocabulary = solvedVocabularyService.getVocabulary(substance);
            SolvedVocabulary solvedVocabulary = solvedVocabularyService.resistSolvedVocabulary(user.get(), vocabulary.get());
            return new ResponseEntity<>(solvedVocabulary, HttpStatus.OK);
        } catch (Exception e) {
            return errorHandler.errorMessage(e);
        }
    }
}
