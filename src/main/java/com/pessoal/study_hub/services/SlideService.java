package com.pessoal.study_hub.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoal.study_hub.exceptions.ResourceNotFoundException;
import com.pessoal.study_hub.models.dtos.SlideColumnRequest;
import com.pessoal.study_hub.models.dtos.SlideRequest;
import com.pessoal.study_hub.models.dtos.SlideResponse;
import com.pessoal.study_hub.models.entities.FileEntity;
import com.pessoal.study_hub.models.entities.Slide;
import com.pessoal.study_hub.models.entities.SlideColumn;
import com.pessoal.study_hub.repositories.SlideRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlideService {

    private static final String TYPE = "slides";

    private final SlideRepository slideRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<SlideResponse> list(Long fileId) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        return slideRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                .map(SlideResponse::from).toList();
    }

    @Transactional
    public SlideResponse create(Long fileId, SlideRequest request) {
        FileEntity file = fileService.findFile(fileId);
        fileService.assertType(file, TYPE);

        Slide slide = Slide.builder()
                .file(file)
                .position((int) slideRepository.countByFileId(fileId))
                .title(request.title() == null ? "" : request.title())
                .layout(request.layout() == null ? "single" : request.layout())
                .titleAlignH(request.titleAlignH() == null ? "center" : request.titleAlignH())
                .titleAlignV(request.titleAlignV() == null ? "center" : request.titleAlignV())
                .bgImage(request.bgImage())
                .build();
        applyColumns(slide, request.columns());

        slide = slideRepository.save(slide);
        fileService.refreshMetadata(fileId);
        return SlideResponse.from(slide);
    }

    @Transactional
    public SlideResponse update(Long fileId, Long slideId, SlideRequest request) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        Slide slide = findSlide(fileId, slideId);

        slide.setTitle(request.title() == null ? "" : request.title());
        slide.setLayout(request.layout() == null ? "single" : request.layout());
        slide.setTitleAlignH(request.titleAlignH() == null ? "center" : request.titleAlignH());
        slide.setTitleAlignV(request.titleAlignV() == null ? "center" : request.titleAlignV());
        slide.setBgImage(request.bgImage());

        slide.getColumns().clear();
        applyColumns(slide, request.columns());

        return SlideResponse.from(slide);
    }

    @Transactional
    public void delete(Long fileId, Long slideId) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        List<Slide> slides = slideRepository.findByFileIdOrderByPositionAsc(fileId);
        slides.removeIf(s -> s.getId().equals(slideId));
        slideRepository.deleteById(slideId);
        for (int i = 0; i < slides.size(); i++) {
            slides.get(i).setPosition(i);
        }
        slideRepository.saveAll(slides);
        fileService.refreshMetadata(fileId);
    }

    private void applyColumns(Slide slide, List<SlideColumnRequest> columnRequests) {
        List<SlideColumn> columns = new ArrayList<>();
        for (int i = 0; i < columnRequests.size(); i++) {
            SlideColumnRequest req = columnRequests.get(i);
            columns.add(SlideColumn.builder()
                    .slide(slide)
                    .position(i)
                    .text(req.text() == null ? "" : req.text())
                    .align(req.align() == null ? "left" : req.align())
                    .image(req.image())
                    .build());
        }
        slide.getColumns().addAll(columns);
    }

    private Slide findSlide(Long fileId, Long slideId) {
        return slideRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                .filter(s -> s.getId().equals(slideId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Slide não encontrado: " + slideId));
    }
}
