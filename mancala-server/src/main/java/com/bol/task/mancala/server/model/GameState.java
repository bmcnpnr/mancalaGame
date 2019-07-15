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
        System.out.println(userMove);
        JsonObject userMoveInJson = new JsonParser().parse(userMove).getAsJsonObject();
        int row = userMoveInJson.get("row").getAsInt();
        int col = userMoveInJson.get("col").getAsInt();
        boolean result = false;
        if (players.get("player1").equals(userSession)) {
            result = getGameBoard().playMove(row, col, Player.PLAYER_ONE);
            for (int i = 0 ; i < 6 ; i++) {
                gameFinished = gameFinished && (getGameBoard().getTable()[1][i] == 0);
            }
        } else if (players.get("player2").equals(userSession)) {
            result = getGameBoard().playMove(row, col, Player.PLAYER_TWO);
            for (int i = 0 ; i < 6 ; i++) {
                gameFinished = gameFinished && (getGameBoard().getTable()[0][i] == 0);
            }
        }
        return result;
    }

    public String calculateWinner() {
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
