package com.goorm.wordsketch.service;

import com.goorm.wordsketch.dto.UserSolvedVocabulary;
import com.goorm.wordsketch.entity.SolvedVocabulary;
import com.goorm.wordsketch.entity.User;
import com.goorm.wordsketch.entity.Vocabulary;

import java.util.List;
import java.util.Optional;

public interface SolvedVocabularyService {
    public Optional<User> getUser(String userEmail);

    public Optional<Vocabulary> getVocabulary(String substance);

    public List<UserSolvedVocabulary> getUserSolvedVocabulary(User user);

    public Optional<SolvedVocabulary> getSolvedVocabulary(User user, Vocabulary vocabulary);

    public SolvedVocabulary resistSolvedVocabulary(User user, Vocabulary vocabulary);

    public UserSolvedVocabulary convertToDto(SolvedVocabulary solvedVocabulary);
}
