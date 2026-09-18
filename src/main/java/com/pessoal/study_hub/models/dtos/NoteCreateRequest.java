package com.pessoal.study_hub.models.dtos;

/** documentId é opcional: ausente/nulo cria uma nota avulsa do tópico. */
public record NoteCreateRequest(Long documentId) {}
