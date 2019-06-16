package com.bol.task.mancala.server.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * In order to support multiple players, hold a game id for each user and assign users to different games
 */
@Component
@Scope("prototype")
public class GameState {
    private Map<String, String> players = new HashMap<>();
    private GameBoard gameBoard;
    private String gameId;

    public GameState() {
        gameBoard = new GameBoard();
        gameBoard.setToInitialState();
        gameId = UUID.randomUUID().toString();
    }

    public Map<String, String> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, String> players) {
        this.players = players;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
