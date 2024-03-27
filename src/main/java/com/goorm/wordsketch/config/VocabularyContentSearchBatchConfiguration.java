package com.goorm.wordsketch.config;

import com.goorm.wordsketch.entity.Vocabulary;
import com.goorm.wordsketch.entity.VocabularyContent;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

// Sprng Batch의 구성을 알기 쉽게 정리한 블로그: https://khj93.tistory.com/entry/Spring-Batch%EB%9E%80-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B3%A0-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
// Spring Batch 5 튜토리얼: https://spring.io/guides/gs/batch-processing
// DB에서 활용하기 위한 레퍼런스: https://docs.spring.io/spring-batch/docs/5.0.2/reference/html/readersAndWriters.html#readersAndWriters 
// Youtube 자막에 포함된 단어를 기준으로 영상을 검색하는 사이트: https://youglish.com/korean
// 디버깅을 위해 참고중인 레퍼런스: https://docs.spring.io/spring-batch/docs/5.0.2/reference/html/whatsnew.html#datasource-transaction-manager-requirement-updates
@SuppressWarnings("null")
@Configuration
@RequiredArgsConstructor
public class VocabularyContentSearchBatchConfiguration {

    public static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd");

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job VocabularyContentSearchBatchJob(PlatformTransactionManager platformTransactionManager, JobRepository jobRepository) {

        return new JobBuilder("VocabularyContentSearchBatchJob", jobRepository).start(VocabularyContentSearchBatchJobStep(jobRepository, platformTransactionManager)).build();
    }

    @Bean
    @JobScope
    public Step VocabularyContentSearchBatchJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("VocabularyContentSearchBatchJobStep", jobRepository).<Vocabulary, VocabularyContent>chunk(10, platformTransactionManager).reader(VocabularyContentSearchBatchReader(null)).writer(VocabularyContentSearchBatchWriter()).build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Vocabulary> VocabularyContentSearchBatchReader(@Value("#{jobParameters[createdDate]}") String createdDate) {

        Map<String, Object> params = new HashMap<>();

        params.put("createdDate", LocalDate.parse(createdDate, FORMATTER));

        String queryString = "SELECT v FROM Vocabulary v WHERE v.createdDate = :createdDate";

        return new JpaPagingItemReaderBuilder<Vocabulary>().name("VocabularyContentSearchBatchReader").entityManagerFactory(entityManagerFactory).pageSize(10).queryString(queryString).parameterValues(params).build();
    }

    @Bean
    public JpaItemWriter<VocabularyContent> VocabularyContentSearchBatchWriter() {
        JpaItemWriter<VocabularyContent> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}