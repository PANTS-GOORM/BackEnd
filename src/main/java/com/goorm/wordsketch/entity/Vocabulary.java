package com.goorm.wordsketch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vocabulary")
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "vocabulary")
    private String vocabulary;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private Integer type;
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;
}
