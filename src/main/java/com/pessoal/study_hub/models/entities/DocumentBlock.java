package com.pessoal.study_hub.models.entities;

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
@Table(name = "document_blocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_file", nullable = false)
    private FileEntity file;

    @Column(name = "\"order\"", nullable = false)
    private Integer position;

    @Column(nullable = false, length = 10)
    private String type;

    @Column(columnDefinition = "text")
    private String contentText;

    @Column(columnDefinition = "text")
    private String linkUrl;

    @Column(columnDefinition = "text")
    private String imageFile;

    @Column(columnDefinition = "text")
    private String documentFile;

    @Column(length = 255)
    private String documentFileName;
}
