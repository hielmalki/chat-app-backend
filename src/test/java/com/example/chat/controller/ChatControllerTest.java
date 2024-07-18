package com.example.chat.controller;


import com.example.chat.model.ChatMessage;
import com.example.chat.service.ChatService;
import com.example.chat.service.DialogflowService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @MockBean
    private DialogflowService dialogflowService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartChat() throws Exception {
        mockMvc.perform(post("/chat/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\": 1}"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testGetMessages() {
        UUID sessionId = UUID.randomUUID();
        List<ChatMessage> messages = new ArrayList<>();

        messages.add(ChatMessage.builder().sessionId(sessionId).sender("Customer").content("Hello").build());
        messages.add(ChatMessage.builder().sessionId(sessionId).sender("Support").content("Hi there!").build());

        when(chatService.getMessages(any(UUID.class))).thenReturn(messages);

        mockMvc.perform(get("/chat/messages/" + sessionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sessionId").value(sessionId.toString()))
                .andExpect(jsonPath("$[0].sender").value("Customer"))
                .andExpect(jsonPath("$[0].content").value("Hello"))
                .andExpect(jsonPath("$[1].sessionId").value(sessionId.toString()))
                .andExpect(jsonPath("$[1].sender").value("Support"))
                .andExpect(jsonPath("$[1].content").value("Hi there!"));
    }

    @Test
    void testSendMessage() throws Exception {
        UUID sessionId = UUID.randomUUID();
        ChatMessage message = ChatMessage.builder().sessionId(sessionId).sender("Customer").content("Hello").build();
        ChatMessage createdMessage = ChatMessage.builder().sessionId(sessionId).sender("Customer").content("Hello").build();

        when(chatService.sendMessage(any(ChatMessage.class))).thenReturn(createdMessage);

        mockMvc.perform(post("/chat/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sessionId\": \"" + sessionId + "\", \"sender\": \"Customer\", \"content\": \"Hello\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value(sessionId.toString()))
                .andExpect(jsonPath("$.sender").value("Customer"))
                .andExpect(jsonPath("$.content").value("Hello"));
    }

    @Test
    void testGetChatbotResponse() throws Exception {
        when(dialogflowService.detectIntentTexts(any(String.class), any(String.class), any(String.class)))
                .thenReturn("Hi there!");

        mockMvc.perform(post("/chat/chatbot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prompt\": \"Hello\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hi there!"));
    }
}
