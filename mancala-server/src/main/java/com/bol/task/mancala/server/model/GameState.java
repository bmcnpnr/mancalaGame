package com.bol.task.mancala.server.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
@Scope("prototype")
public class GameState {
    private WebSocketSession currentPlayer;
    private GameBoard gameBoard;

    public GameState() {
        gameBoard = new GameBoard();
        gameBoard.setToInitialState();
    }

    public void clearGameState() throws IOException {
        gameBoard.setToInitialState();
        currentPlayer.close();
    }

    public WebSocketSession getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(WebSocketSession currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
