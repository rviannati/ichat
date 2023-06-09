package com.ichat.controller;

import com.ichat.model.request.ChatBotInputRequest;
import com.ichat.model.response.ChatGPTResponse;
import com.ichat.service.ChatGPTService;
import com.ichat.service.Espeak;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatGPTController {

    private ChatGPTService chatGPTService;

    private Espeak espeak = new Espeak();

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatGPTResponse> processInputRequest(@RequestBody ChatBotInputRequest chatbotInputRequest) {
        ChatGPTResponse chatCPTResponse = chatGPTService.getChatGPTResponse(chatbotInputRequest.getMessage());

        chatCPTResponse.getChoices().forEach(voice -> espeak.speak(voice.getMessage().content));
//        espeak.speak(chatCPTResponse.getUsage().);
        return new ResponseEntity<>(chatCPTResponse, HttpStatus.OK);
    }
}
