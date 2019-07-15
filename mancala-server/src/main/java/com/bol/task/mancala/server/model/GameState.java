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
    private boolean gameFinished;

    public GameState() {
        gameBoard = new GameBoard();
        gameBoard.setToInitialState();
        gameFinished = true;
        gameId = UUID.randomUUID().toString();
    }

    public boolean playUserMove(String userMove, String userSession) {
        JsonObject userMoveInJson = new JsonParser().parse(userMove).getAsJsonObject();
        int row = userMoveInJson.get("row").getAsInt();
        int col = userMoveInJson.get("col").getAsInt();
        boolean result = false;
        if (players.get("player1").equals(userSession))
            result = getGameBoard().playMove(row, col, Player.PLAYER_ONE);
        else
            result = getGameBoard().playMove(row, col, Player.PLAYER_TWO);
        checkIfGameIsFinished();
        return result;
    }

    private void checkIfGameIsFinished() {
        for (int i = 0 ; i < 2 ; i++) {
            gameFinished = true;
            for (int j = 0 ; j < 6 ; j++) {
                gameFinished = gameFinished && (getGameBoard().getTable()[i][j] == 0);
            }
            if (gameFinished) break;
        }
    }

    public String calculateTheWinner() {
        if (gameFinished) {
            if (getGameBoard().getUserOneScore() == getGameBoard().getUserTwoScore()) { //draw
                return "draw";
            } else if (getGameBoard().getUserOneScore() > getGameBoard().getUserTwoScore()) { //user one wins
                return "player1 won";
            } else { //user two wins
                return "player 2 won";
            }
        }
        return null;
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

    public boolean isGameFinished() {
        return gameFinished;
    }
}
