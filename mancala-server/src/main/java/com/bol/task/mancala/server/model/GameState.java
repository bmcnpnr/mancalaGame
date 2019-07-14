package com.bol.task.mancala.server.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public void playUserMove(String userMove, String userSession) {
        System.out.println(userMove);
        JsonObject userMoveInJson = new JsonParser().parse(userMove).getAsJsonObject();
        int row = userMoveInJson.get("row").getAsInt();
        int col = userMoveInJson.get("col").getAsInt();
        if (players.get("player1").equals(userSession)) {
            playerOneMove(row, col);
        } else if (players.get("player2").equals(userSession)) {
            playerTwoMove(row, col);
        }
    }

    private void playerOneMove(int row, int col) {
        boolean result = getGameBoard().playMove(row, col, Player.PLAYER_ONE);
    }

    private void playerTwoMove(int row, int col) {
        boolean result = getGameBoard().playMove(row - 1, (5 - col), Player.PLAYER_TWO);
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
