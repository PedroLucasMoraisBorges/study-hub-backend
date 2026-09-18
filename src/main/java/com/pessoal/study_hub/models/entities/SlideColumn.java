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
@Table(name = "slide_columns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlideColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_slide", nullable = false)
    private Slide slide;

    @Column(name = "\"order\"", nullable = false)
    private Integer position;

    @Column(nullable = false, columnDefinition = "text")
    @Builder.Default
    private String text = "";

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String align = "left";

    @Column(columnDefinition = "text")
    private String image;
}
