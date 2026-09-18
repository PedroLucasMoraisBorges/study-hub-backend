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
@Table(name = "mindmap_nodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MindMapNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_file", nullable = false)
    private FileEntity file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_parent")
    private MindMapNode parent;

    @Column(nullable = false, columnDefinition = "text")
    @Builder.Default
    private String label = "";

    @Column(nullable = false, columnDefinition = "text")
    @Builder.Default
    private String description = "";

    @Column(name = "pos_x", nullable = false)
    private Double posX;

    @Column(name = "pos_y", nullable = false)
    private Double posY;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String shape = "rectangle";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_color")
    private Color color;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String borderStyle = "none";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_border_color")
    private Color borderColor;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String fontSize = "medium";
}
