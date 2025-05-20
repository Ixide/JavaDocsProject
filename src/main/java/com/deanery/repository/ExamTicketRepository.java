package com.deanery.repository;

import com.deanery.model.ExamTicket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamTicketRepository extends MongoRepository<ExamTicket, String> {
}