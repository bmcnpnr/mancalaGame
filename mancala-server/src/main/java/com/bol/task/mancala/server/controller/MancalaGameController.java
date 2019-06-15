package com.bol.task.mancala.server.controller;

import com.bol.task.mancala.server.service.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MancalaGameController {

    @Autowired
    private GameManager gameManager;

    @MessageMapping("/userMove")
    @SendTo("/topic/mancala-notifications")
    public String userMove(int indexOfBucket) {
        return "Hello";
    }
}
