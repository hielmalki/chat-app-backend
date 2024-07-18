# Chat Application Backend

This is the backend service for the Chat Application. It is built using Spring Boot and provides RESTful APIs for managing chat sessions, sending messages, and interacting with a chatbot.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [License](#license)

## Features
- Start a new chat session
- Send and receive messages in a chat session
- Interact with a chatbot to get automated responses

## Technologies Used
- Java 11
- Spring Boot
- RESTful APIs
- Lombok
- Maven

## Installation

### Prerequisites
- Java 11 or higher
- Maven
- Git

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/hielmalki/chat-app-backend.git
   cd chat-app-backend

Install dependencies:

2. **Install dependencies**
   ```bash
   mvn clean install

3. **Google Cloud Service Account**
To interact with Dialogflow, ensure you have your Google Cloud Service Account credentials file. Place it in src/main/resources/credentials.json and add the following property:
    ```bash
   google.cloud.credentials.location=src/main/resources/credentials.json

4. **Running the Application**

    To run the application, execute the following command in the project root directory:
    ```bash
      mvn spring-boot:run

