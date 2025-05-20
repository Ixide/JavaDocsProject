package com.deanery.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "exam_tickets")
public class ExamTicket {
    @Id
    private String id;
    private String disciplineName;
    private List<String> questions;
}