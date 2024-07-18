package com.example.chat.controller;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.ChatSession;
import com.example.chat.service.ChatService;
import com.example.chat.service.DialogflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private DialogflowService dialogflowService;

    /**
     * Starts a new chat session.
     *
     * @param session the chat session details
     * @return the created chat session
     */
    @PostMapping("/start")
    public ResponseEntity<ChatSession> startChat(@RequestBody ChatSession session) {
        log.info("POST /chat/start/ called with sessionId {}", session.toString());
        ChatSession createdSession = chatService.startChat(session);
        return ResponseEntity.ok(createdSession);
    }

    /**
     * Sends a message in an existing chat session.
     *
     * @param message the chat message details
     * @return the created chat message
     */
    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        log.info("POST /chat/send/ called with message {}", message.getContent());
        System.out.println("Received message: " + message);

        if (message.getSessionId() == null || message.getSender() == null || message.getContent() == null) {
            return ResponseEntity.badRequest().build();
        }

        ChatMessage createdMessage = chatService.sendMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    /**
     * Retrieves all messages for a given session.
     *
     * @param sessionId the session ID
     * @return the list of chat messages for the session
     */
    @GetMapping("/messages/{sessionId}")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable UUID sessionId) {
        List<ChatMessage> messages = chatService.getMessages(sessionId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Gets a response from the chatbot.
     *
     * @param prompt the user's input text
     * @return the chatbot's response
     */
    @PostMapping("/chatbot")
    public ResponseEntity<String> getChatbotResponse(@RequestBody String prompt) {
        log.info("GET /chat/chatbot/{} called", prompt);
        try {
            String sessionId = UUID.randomUUID().toString(); // Ersetzen Sie dies durch eine eindeutige Sitzungs-ID
            String languageCode = "de"; // oder eine andere Sprache
            String response = dialogflowService.detectIntentTexts(prompt, sessionId, languageCode);
            log.debug("AI Chatbot response: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing request: " + e.getMessage());
        }
    }
}
