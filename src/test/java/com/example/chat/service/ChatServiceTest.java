package com.example.chat.service;


import com.example.chat.model.ChatMessage;
import com.example.chat.model.ChatSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChatServiceTest {

    private ChatService chatService;

    @BeforeEach
    void setUp() {
        chatService = new ChatService();
    }

    @Test
    void testStartChat() {
        ChatSession session = new ChatSession();
        ChatSession createdSession = chatService.startChat(session);

        assertNotNull(createdSession.getId());
        assertEquals("active", createdSession.getStatus());
        assertTrue(chatService.getMessages(createdSession.getId()).isEmpty());
    }

    @Test
    void testSendMessage() {
        ChatSession session = new ChatSession();
        ChatSession createdSession = chatService.startChat(session);

        ChatMessage message = new ChatMessage();
        message.setSessionId(createdSession.getId());
        message.setSender("Customer");
        message.setContent("Hello");

        ChatMessage createdMessage = chatService.sendMessage(message);

        assertNotNull(createdMessage.getId());
        assertEquals("Customer", createdMessage.getSender());
        assertEquals("Hello", createdMessage.getContent());
        assertNotNull(createdMessage.getTimestamp());

        List<ChatMessage> messages = chatService.getMessages(createdSession.getId());
        assertEquals(1, messages.size());
        assertEquals(createdMessage, messages.get(0));
    }

    @Test
    void testGetMessages() {
        ChatSession session = new ChatSession();
        ChatSession createdSession = chatService.startChat(session);

        ChatMessage message1 = new ChatMessage();
        message1.setSessionId(createdSession.getId());
        message1.setSender("Customer");
        message1.setContent("Hello");
        chatService.sendMessage(message1);

        ChatMessage message2 = new ChatMessage();
        message2.setSessionId(createdSession.getId());
        message2.setSender("Support");
        message2.setContent("Hi there!");
        chatService.sendMessage(message2);

        List<ChatMessage> messages = chatService.getMessages(createdSession.getId());

        assertEquals(2, messages.size());
        assertEquals(message1.getContent(), messages.get(0).getContent());
        assertEquals(message2.getContent(), messages.get(1).getContent());
    }

    @Test
    void testSendSupportMessage() {
        ChatSession session = new ChatSession();
        ChatSession createdSession = chatService.startChat(session);

        ChatMessage message = new ChatMessage();
        message.setSessionId(createdSession.getId());
        message.setSender("Support");
        message.setContent("How can I help you?");

        ChatMessage createdMessage = chatService.sendSupportMessage(message);

        assertNotNull(createdMessage.getId());
        assertEquals("Support", createdMessage.getSender());
        assertEquals("How can I help you?", createdMessage.getContent());
        assertNotNull(createdMessage.getTimestamp());

        List<ChatMessage> messages = chatService.getMessages(createdSession.getId());
        assertEquals(1, messages.size());
        assertEquals(createdMessage, messages.get(0));
    }
}
