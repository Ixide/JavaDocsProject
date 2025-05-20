package com.deanery.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "syllabuses")
public class Syllabus {
    @Id
    private String id;
    private String disciplineName;
    private List<String> lectureTopics;
    private List<String> practiceTopics;
    private List<String> literature;
    private String assessmentCriteria;
    private String assessmentSystem; // e.g., "балльно-рейтинговая", "зачет/незачет"
}