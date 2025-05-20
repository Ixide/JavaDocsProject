package com.deanery.repository;

import com.deanery.model.Syllabus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SyllabusRepository extends MongoRepository<Syllabus, String> {
}