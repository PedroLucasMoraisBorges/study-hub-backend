package com.pessoal.study_hub.models.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "slides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_file", nullable = false)
    private FileEntity file;

    @Column(name = "\"order\"", nullable = false)
    private Integer position;

    @Column(nullable = false, columnDefinition = "text")
    @Builder.Default
    private String title = "";

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String layout = "single";

    @Column(name = "title_align_h", nullable = false, length = 10)
    @Builder.Default
    private String titleAlignH = "center";

    @Column(name = "title_align_v", nullable = false, length = 10)
    @Builder.Default
    private String titleAlignV = "center";

    @Column(columnDefinition = "text")
    private String bgImage;

    @OneToMany(mappedBy = "slide", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    @Builder.Default
    private List<SlideColumn> columns = new ArrayList<>();
}
