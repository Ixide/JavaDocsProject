package com.deanery.service;

import com.deanery.model.Syllabus;
import com.deanery.model.ExamTicket;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class DocumentService {

    public void generateSyllabusDocument(Syllabus syllabus, String filePath) throws IOException {
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph title = document.createParagraph();
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Syllabus for " + syllabus.getDisciplineName());
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            XWPFParagraph lectures = document.createParagraph();
            lectures.createRun().setText("Lecture Topics:");
            for (String topic : syllabus.getLectureTopics()) {
                XWPFParagraph p = document.createParagraph();
                p.createRun().setText("- " + topic);
            }

            XWPFParagraph practices = document.createParagraph();
            practices.createRun().setText("Practice Topics:");
            for (String topic : syllabus.getPracticeTopics()) {
                XWPFParagraph p = document.createParagraph();
                p.createRun().setText("- " + topic);
            }

            XWPFParagraph literature = document.createParagraph();
            literature.createRun().setText("Literature:");
            for (String item : syllabus.getLiterature()) {
                XWPFParagraph p = document.createParagraph();
                p.createRun().setText("- " + item);
            }

            XWPFParagraph assessment = document.createParagraph();
            assessment.createRun().setText("Assessment Criteria: " + syllabus.getAssessmentCriteria());
            assessment.createRun().setText("Assessment System: " + syllabus.getAssessmentSystem());

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }
        }
    }

    public void generateExamTicketDocument(ExamTicket ticket, String filePath) throws IOException {
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph title = document.createParagraph();
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Exam Ticket for " + ticket.getDisciplineName());
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            XWPFParagraph questions = document.createParagraph();
            questions.createRun().setText("Questions:");
            for (String question : ticket.getQuestions()) {
                XWPFParagraph p = document.createParagraph();
                p.createRun().setText("- " + question);
            }

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }
        }
    }
}