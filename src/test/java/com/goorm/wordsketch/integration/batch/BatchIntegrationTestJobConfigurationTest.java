package com.goorm.wordsketch.integration.batch;

import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyContent;
import com.goorm.wordsketch.entity.VocabularyType;
import com.goorm.wordsketch.repository.VocabularyContentRepository;
import com.goorm.wordsketch.repository.VocabularyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SuppressWarnings("null")
@SpringBatchTest
@SpringBootTest
@DisplayName("어휘 관련 컨텐츠 수집 Batch Process 테스트")
public class BatchIntegrationTestJobConfigurationTest {

  public static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd");

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Autowired
  private VocabularyRepository vocabularyRepository;

  @Autowired
  private VocabularyContentRepository vocabularyContentRepository;

  /**
   * 각 테스트가 끝나고, 적용된 변경사항들을 초기화하는 로직
   * 
   * @throws Exception
   */
  @AfterEach
  public void tearDown() throws Exception {

    vocabularyRepository.deleteAllInBatch();
    vocabularyContentRepository.deleteAllInBatch();
  }

  @Nested
  @DisplayName("Given: DB에 새로 등록된 어휘가 존재할 때")
  class Given_DB에_새로_등록된_어휘가_존재할_때 {

    LocalDate createdDate = LocalDate.now();

    Vocabulary vocabulary = Vocabulary.builder()
        .vocabulary("이듬해")
        .description("어떤 일이 있은 그 다음해. 익년(翌年).")
        .type(VocabularyType.단어)
        .build();

    Vocabulary savedVocabulary = vocabularyRepository.save(vocabulary);

    // Batch의 인자로 오늘 날짜를 넘겨서, 오늘 등록된 어휘만 대상으로 실행
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("createdDate", createdDate.format(FORMATTER))
        .toJobParameters();

    @Nested
    @DisplayName("When: 배치가 실행되면")
    class When_배치가_실행되면 {

      JobExecution jobExecution;

      @Test
      @DisplayName("Then: 어휘와 관련된 컨텐츠를 찾아 DB에 등록한다")
      void Then_어휘와_관련된_컨텐츠를_찾아_DB에_등록한다() throws Exception {

        jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        List<VocabularyContent> vocabularyContents = vocabularyContentRepository.findAll();
        assertSame(1, vocabularyContents.size());
      }
    }
  }
}
