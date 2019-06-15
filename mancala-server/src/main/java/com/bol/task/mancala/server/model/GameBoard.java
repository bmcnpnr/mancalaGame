package com.bol.task.mancala.server.model;


/**
 * First row will represent the first player's board.
 * Second row will represent the second player's board.
 */
public class GameBoard {
    int[][] board;
    public GameBoard() {

    }

    public void setToInitialState() {
        board = new int[2][7];
        for (int i = 0 ; i < 2 ; i++) {
            for (int j = 1; j < 6 ; j++) {
                board[i][j] = 6;
            }
        }
    }
}
