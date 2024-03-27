package com.goorm.wordsketch.unit.vocabulary;

import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyType;
import com.goorm.wordsketch.repository.VocabularyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("VocabularyRepository 기능 테스트")
public class VocabularyRepositoryTest {

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @SuppressWarnings("null")
    @Nested
    @DisplayName("Given: 임의의 Vocabulary Entity가 있고")
    class givenTempVocabularyEntity {

        Vocabulary vocabulary = Vocabulary.builder()
                .substance("테용 테스용 테스트용 ")
                .description("테스트용 설명입니다.")
                .type(VocabularyType.단어)
                .build();

        @Nested
        @DisplayName("When: 연결된 DB에 저장을 요청하면")
        class requestSaveEntityInDB {

            Vocabulary savedVocabulary = vocabularyRepository.save(vocabulary);

            @Test
            @DisplayName("Then: 정상적으로 Entity가 DB에 저장된다")
            void saveEntityCorrectly() {

                assertEquals(vocabulary, savedVocabulary);
            }
        }
    }
}
