package com.bol.task.mancala.server.model;


/**
 * First row will represent the first player's board.
 * Second row will represent the second player's board.
 */
public class GameBoard {
    private int[][] table;
    private int userOneScore = 0;
    private int userTwoScore = 0;

    GameBoard() {

    }

    void setToInitialState() {
        table = new int[2][6];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                table[i][j] = 6;
            }
        }
    }

    boolean playMove(int row, int col, Player player) {
        int numOfStones = table[row][col];
        table[row][col] = 0;
        if (row == 0) {
            if (col != 0)
                return playMove(row, col - 1, numOfStones, player);
            else if (Player.PLAYER_ONE.equals(player)) {
                userOneScore++;
                return playMove(row + 1, col, numOfStones - 1, player);
            } else {
                return playMove(row + 1, col, numOfStones, player);
            }
        } else if (row == 1) {
            if (col != 5)
                return playMove(row, col + 1, numOfStones, player);
            else if (Player.PLAYER_TWO.equals(player)) {
                userTwoScore++;
                return playMove(row - 1, col, numOfStones - 1, player);
            } else {
                return playMove(row - 1, col, numOfStones, player);
            }
        }
        return false;
    }

    private boolean playMove(int row, int col, int numOfStones, Player player) {
        if (numOfStones == 0) {
            if (Player.PLAYER_ONE.equals(player) && row == 1 && col == 0)
                return true;
            else return Player.PLAYER_TWO.equals(player) && row == 0 && col == 5;
        }
        if (row == 0 && col >= 0) {
            if (Player.PLAYER_ONE.equals(player) && col == 0) {
                table[row][col] = table[row][col] + 1;
                if (numOfStones > 1) {
                    userOneScore++;
                    return playMove(row + 1, col, numOfStones - 2, player);
                } else return numOfStones == 1;
            } else if (col == 0) {
                table[row][col] = table[row][col] + 1;
                return playMove(row + 1, col, numOfStones - 1, player);
            }  else {
                table[row][col] = table[row][col] + 1;
                return playMove(row, col - 1, numOfStones - 1, player);
            }
        } else if (row == 1 && col <= 5) {
            if (Player.PLAYER_TWO.equals(player) && col == 5) {
                table[row][col] = table[row][col] + 1;
                if (numOfStones > 1) {
                    userTwoScore++;
                    return playMove(row - 1, col, numOfStones - 2, player);
                } else return numOfStones == 1;
            } else if (col == 5) {
                table[row][col] = table[row][col] + 1;
                return playMove(row - 1, col, numOfStones - 1, player);
            } else {
                table[row][col] = table[row][col] + 1;
                return playMove(row, col + 1, numOfStones - 1, player);
            }
        }
        return false;
    }
}