package com.pessoal.study_hub.models.entities;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    public static final String DEFAULT_COLOR = "butter";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_topic", nullable = false)
    private Topic topic;

    /** Nulo quando a nota é avulsa (presa apenas ao tópico). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_file")
    private FileEntity file;

    @Column(columnDefinition = "text")
    private String text;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String color = DEFAULT_COLOR;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
