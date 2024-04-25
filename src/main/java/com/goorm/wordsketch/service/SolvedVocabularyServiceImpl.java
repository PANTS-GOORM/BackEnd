package com.goorm.wordsketch.service;

import com.goorm.wordsketch.dto.UserSolvedVocabulary;
import com.goorm.wordsketch.entity.SolvedVocabulary;
import com.goorm.wordsketch.entity.User;
import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.repository.SolvedVocabularyRepository;
import com.goorm.wordsketch.repository.UserRepository;
import com.goorm.wordsketch.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolvedVocabularyServiceImpl implements SolvedVocabularyService {
    private final SolvedVocabularyRepository solvedVocabularyRepository;
    private final UserRepository userRepository;
    private final VocabularyRepository vocabularyRepository;

    @Autowired
    public SolvedVocabularyServiceImpl(SolvedVocabularyRepository solvedVocabularyRepository, UserRepository userRepository, VocabularyRepository vocabularyRepository) {
        this.solvedVocabularyRepository = solvedVocabularyRepository;
        this.userRepository = userRepository;
        this.vocabularyRepository = vocabularyRepository;
    }

    /*
     * 유저 반환
     *
     * @param userEmail 유저 이메일
     * @return user 유저 반환
     * */
    @Override
    public Optional<User> getUser(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        return user;
    }

    /*
     * 어휘 반환
     *
     * @param substance 어휘 이름
     * @return vocabulary 어휘
     * */
    @Override
    public Optional<Vocabulary> getVocabulary(String substance) {
        Optional<Vocabulary> vocabulary = vocabularyRepository.findBySubstance(substance);
        return vocabulary;
    }

    /*
     * 유저가 풀었던 문제리스트 반환
     *
     * @param user 유저
     * @return vocabularyList 유저가 풀었던 문제리스트
     * */
    public List<UserSolvedVocabulary> getUserSolvedVocabulary(User user) {
        List<SolvedVocabulary> vocabularyList = solvedVocabularyRepository.findByUser(user);
        List<UserSolvedVocabulary> userSolvedVocabularyList = new ArrayList<>();
        for (SolvedVocabulary sv : vocabularyList) {
            userSolvedVocabularyList.add(convertToDto(sv));
        }

        return userSolvedVocabularyList;
    }

    /*
     * 유저가 풀었던 문제 반환
     *
     * @param user 유저
     * @param vocabulary 문제
     * @return findVocabulary 유저가 풀었던 문제
     * */
    public Optional<SolvedVocabulary> getSolvedVocabulary(User user, Vocabulary vocabulary) {
        Optional<SolvedVocabulary> findVocabulary = solvedVocabularyRepository.findByUserAndVocabulary(user, vocabulary);
        return findVocabulary;
    }

    /*
     * 푼 문제 등록
     *
     * @param user 유저
     * @param vocabulary 문제
     * @return 푼 문제
     * */
    @Override
    public SolvedVocabulary resistSolvedVocabulary(User user, Vocabulary vocabulary) {
        SolvedVocabulary solvedVocabulary = null;
        Optional<SolvedVocabulary> findSolvedVocabulary = getSolvedVocabulary(user, vocabulary);
        if (findSolvedVocabulary.isPresent()) {
            solvedVocabulary = findSolvedVocabulary.get();
        } else {
            solvedVocabulary = SolvedVocabulary.builder()
                    .user(user)
                    .vocabulary(vocabulary)
                    .build();
        }

        return solvedVocabularyRepository.save(solvedVocabulary);
    }

    public UserSolvedVocabulary convertToDto(SolvedVocabulary solvedVocabulary) {
        UserSolvedVocabulary dto = new UserSolvedVocabulary(solvedVocabulary.getVocabulary().getSubstance()
                , solvedVocabulary.getVocabulary().getDescription()
                , solvedVocabulary.getCreatedDate().toLocalDateTime().getMonthValue());
        return dto;
    }

}
