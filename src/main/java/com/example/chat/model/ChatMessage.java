package com.example.chat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;


@SuperBuilder
@Data
@NoArgsConstructor
public class ChatMessage {
    private UUID id;
    private UUID sessionId;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
}
