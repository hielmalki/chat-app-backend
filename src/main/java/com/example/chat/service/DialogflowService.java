package com.example.chat.service;

import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author helmalki
 *
 * This service handles interactions with the Dialogflow API.
 * It provides methods to detect intents from user input texts.
 */
@Service
public class DialogflowService {

    @Value("${dialogflow.project.id}")
    private String projectId;

    @Value("${dialogflow.credentials.path}")
    private String credentialsPath;

    /**
     * Detects the intent of a given text using Dialogflow.
     *
     * @param text the user's input text
     * @param sessionId the session ID for the Dialogflow session
     * @param languageCode the language code for the input text
     * @return the fulfillment text from Dialogflow's response
     * @throws IOException if there is an issue with reading the credentials file
     * @throws ApiException if there is an issue with the Dialogflow API request
     */
    public String detectIntentTexts(String text, String sessionId, String languageCode) throws IOException, ApiException {
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(() -> GoogleCredentials.fromStream(new FileInputStream(credentialsPath)))
                .build();

        try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
            SessionName session = SessionName.of(projectId, sessionId);

            TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            QueryResult queryResult = response.getQueryResult();
            return queryResult.getFulfillmentText();
        }
    }
}
