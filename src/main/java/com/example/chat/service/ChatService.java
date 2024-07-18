package com.example.chat.service;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.ChatSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author helmalki
 *
 * This service handles chat operations such as starting a new chat session,
 * sending messages, and retrieving messages for a chat session.
 */
@Service
@Slf4j
public class ChatService {

    private Map<UUID, ChatSession> chatSessions = new HashMap<>();
    private List<ChatMessage> messages = new ArrayList<>();

    /**
     * Starts a new chat session.
     *
     * @param session the chat session details
     * @return the created chat session
     */
    public ChatSession startChat(ChatSession session) {
        log.debug("Starts a new chat session...");
        session.setId(UUID.randomUUID());
        session.setStatus("active");
        chatSessions.put(session.getId(), session);
        log.info("Session started");
        return session;
    }

    /**
     * Sends a message in an existing chat session.
     *
     * @param message the chat message details
     * @return the created chat message
     */
    public ChatMessage sendMessage(ChatMessage message) {
        log.debug("Sends a message in an existing chat session...");
        message.setId(UUID.randomUUID());
        message.setTimestamp(LocalDateTime.now());
        messages.add(message);
        ChatSession session = chatSessions.get(message.getSessionId());
        if (session != null) {
            session.getMessages().add(message);
        }
        log.debug("Message sent");
        return message;
    }

    /**
     * Retrieves all messages for a given session.
     *
     * @param sessionId the session ID
     * @return the list of chat messages for the session
     */
    public List<ChatMessage> getMessages(UUID sessionId) {
        log.debug("Retrieve all messages");
        ChatSession session = chatSessions.get(sessionId);
        return session != null ? session.getMessages() : new ArrayList<>();
    }
}
