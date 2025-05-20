package com.deanery.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Dotenv dotenv = Dotenv.load();
    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    public List<String> generateTopics(String discipline, String type) throws IOException {
        String prompt = String.format("Generate a list of 10 %s topics for the discipline: %s", type, discipline);
        JsonNode response = callOpenAI(prompt);
        String content = response.get("choices").get(0).get("message").get("content").asText();
        List<String> topics = new ArrayList<>();
        for (String topic : content.split("\n")) {
            if (!topic.trim().isEmpty()) {
                topics.add(topic.trim());
            }
        }
        return topics;
    }

    public List<String> generateExamQuestions(String discipline) throws IOException {
        String prompt = String.format("Generate 10 exam questions for the discipline: %s", discipline);
        JsonNode response = callOpenAI(prompt);
        String content = response.get("choices").get(0).get("message").get("content").asText();
        List<String> questions = new ArrayList<>();
        for (String question : content.split("\n")) {
            if (!question.trim().isEmpty()) {
                questions.add(question.trim());
            }
        }
        return questions;
    }

    private JsonNode callOpenAI(String prompt) throws IOException {
        String json = String.format("""
                   {
                       "model": "gpt-3.5-turbo",
                       "messages": [{"role": "user", "content": "%s"}],
                       "max_tokens": 500
                   }
                   """, prompt);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + dotenv.get("OPENAI_API_KEY"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return objectMapper.readTree(response.body().string());
        }
    }
}