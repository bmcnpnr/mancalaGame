package com.bol.task.mancala.server.controller;

import com.bol.task.mancala.server.model.GameBoard;
import com.bol.task.mancala.server.model.GameState;
import com.bol.task.mancala.server.service.GameManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class MancalaGameController {

    @Autowired
    private GameManager gameManager;

    @MessageMapping("/connectToGame")
    @SendTo("/topic/mancala-notifications")
    public String connectToGame(SimpMessageHeaderAccessor headerAccessor) {
        //todo: check if session exist. If it does, do not init the game and just return the result to the client
        gameManager.connectTheUser(headerAccessor.getSessionAttributes().get("sessionId").toString());
        GameState gameState = gameManager.getGameState(headerAccessor.getSessionAttributes().get("sessionId").toString());
        GameBoard gameBoard = gameState.getGameBoard();
        JsonObject gameBoardJson = new JsonParser().parse(new Gson().toJson(gameBoard)).getAsJsonObject();
        if (gameState.getPlayers().get("player1").equals(headerAccessor.getSessionAttributes().get("sessionId").toString())) {
            gameBoardJson.addProperty("playerOfThisClient", "player1");
        } else if (gameState.getPlayers().get("player2").equals(headerAccessor.getSessionAttributes().get("sessionId").toString())) {
            gameBoardJson.addProperty("playerOfThisClient", "player2");
        }
        gameBoardJson.addProperty("nextPlayerToPlay", "player1");
        return gameBoardJson.toString();
    }


    @MessageMapping("/userMove")
    @SendTo("/topic/mancala-notifications")
    public String userMove(@Payload String userMove, SimpMessageHeaderAccessor headerAccessor) {
        GameState gameState = gameManager.getGameState(headerAccessor.getSessionAttributes().get("sessionId").toString());
        boolean willThePlayerPlayAgain = gameState.playUserMove(userMove, headerAccessor.getSessionAttributes().get("sessionId").toString());
        GameBoard gameBoard = gameState.getGameBoard();
        if (gameState.isGameFinished()) {
            JsonObject gameFinishedJson = new JsonObject();
            gameFinishedJson.addProperty("gameFinished", gameState.calculateWinner());
            return gameFinishedJson.toString();
        }
        JsonObject gameBoardJson = new JsonParser().parse(new Gson().toJson(gameBoard)).getAsJsonObject();
        if (headerAccessor.getSessionAttributes().get("sessionId").toString().equals(gameState.getPlayers().get("player1"))) {
            gameBoardJson.addProperty("playerOfThisClient", "player1");
            if (willThePlayerPlayAgain)
                gameBoardJson.addProperty("nextPlayerToPlay", "player1");
            else
                gameBoardJson.addProperty("nextPlayerToPlay", "player2");

        } else if (headerAccessor.getSessionAttributes().get("sessionId").toString().equals(gameState.getPlayers().get("player2"))) {
            gameBoardJson.addProperty("playerOfThisClient", "player2");
            if (willThePlayerPlayAgain)
                gameBoardJson.addProperty("nextPlayerToPlay", "player2");
            else
                gameBoardJson.addProperty("nextPlayerToPlay", "player1");
        }
        return gameBoardJson.toString();
    }
}
