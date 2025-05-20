package com.deanery.controller;

import com.deanery.model.ExamTicket;
import com.deanery.repository.ExamTicketRepository;
import com.deanery.service.DocumentService;
import com.deanery.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/exam-ticket")
@RequiredArgsConstructor
public class ExamTicketController {
    private final OpenAIService openAIService;
    private final DocumentService documentService;
    private final ExamTicketRepository examTicketRepository;

    @PostMapping("/generate")
    public ExamTicket generateExamTicket(@RequestParam String discipline) throws IOException {
        ExamTicket ticket = new ExamTicket();
        ticket.setDisciplineName(discipline);
        ticket.setQuestions(openAIService.generateExamQuestions(discipline));

        examTicketRepository.save(ticket);
        documentService.generateExamTicketDocument(ticket, "exam_ticket_" + discipline + ".docx");
        return ticket;
    }

    @GetMapping
    public List<ExamTicket> getAllExamTickets() {
        return examTicketRepository.findAll();
    }
}