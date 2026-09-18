package com.pessoal.study_hub.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.study_hub.models.dtos.ColorResponse;
import com.pessoal.study_hub.repositories.ColorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorRepository colorRepository;

    @GetMapping
    public List<ColorResponse> list() {
        return colorRepository.findAll().stream().map(ColorResponse::from).toList();
    }
}
