package com.deanery.controller;

import com.deanery.model.Syllabus;
import com.deanery.repository.SyllabusRepository;
import com.deanery.service.DocumentService;
import com.deanery.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/syllabus")
@RequiredArgsConstructor
public class SyllabusController {
    private final OpenAIService openAIService;
    private final DocumentService documentService;
    private final SyllabusRepository syllabusRepository;

    @PostMapping("/generate")
    public Syllabus generateSyllabus(@RequestParam String discipline, @RequestParam String specialty) throws IOException {
        Syllabus syllabus = new Syllabus();
        syllabus.setDisciplineName(discipline);
        syllabus.setLectureTopics(openAIService.generateTopics(discipline, "lecture"));
        syllabus.setPracticeTopics(openAIService.generateTopics(discipline, "practice"));
        syllabus.setLiterature(openAIService.generateTopics(String.format("%s for %s specialty", discipline, specialty), "literature"));
        syllabus.setAssessmentCriteria("Criteria TBD");
        syllabus.setAssessmentSystem("балльно-рейтинговая");

        syllabusRepository.save(syllabus);
        documentService.generateSyllabusDocument(syllabus, "syllabus_" + discipline + ".docx");
        return syllabus;
    }

    @GetMapping
    public List<Syllabus> getAllSyllabuses() {
        return syllabusRepository.findAll();
    }
}