package com.example.chat.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ChatSession {
    private UUID id;
    private int customerId;
    private Integer agentId;
    private List<ChatMessage> messages = new ArrayList<>();
    private String status;
}
