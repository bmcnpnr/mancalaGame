package com.bol.task.mancala.server.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameStateTests {

    private GameState gameState;

    @Before
    public void initTest() {
        gameState = new GameState();

        gameState.getPlayers().put("player1", "user1");
        gameState.getPlayers().put("player2", "user2");
    }

    @Test
    public void testGameMoves() {
        gameState.playUserMove("{row: 1, col:4}", "user1");
        gameState.playUserMove("{row: 0, col:4}", "user2");
        assertEquals(new int[][]{{7, 7, 8, 8, 0, 7}, {7, 7, 6, 6, 0, 7}}, gameState.getGameBoard().getTable());
        assertEquals(1, gameState.getGameBoard().getUserOneScore());
        assertEquals(1, gameState.getGameBoard().getUserTwoScore());
        assertEquals(Player.PLAYER_ONE, gameState.getNextUserToPlay());
        assertFalse(gameState.isGameFinished());
    }

    @Test
    public void testConsecutiveGameMovesByTheSamePlayer() {
        gameState.playUserMove("{row: 1, col:4}", "user1");
        assertEquals(new int[][]{{6, 6, 7, 7, 7, 7}, {6, 6, 6, 6, 0, 7}}, gameState.getGameBoard().getTable());
        gameState.playUserMove("{row: 1, col:4}", "user1");
        assertEquals(new int[][]{{6, 6, 7, 7, 7, 7}, {6, 6, 6, 6, 0, 7}}, gameState.getGameBoard().getTable());
        assertEquals(1, gameState.getGameBoard().getUserOneScore());
        assertEquals(0, gameState.getGameBoard().getUserTwoScore());
        assertEquals(Player.PLAYER_TWO, gameState.getNextUserToPlay());
        assertFalse(gameState.isGameFinished());
    }

    @Test
    public void testIllegalMoveByPlayers() {
        assertEquals(new int[][]{{6, 6, 6, 6, 6, 6}, {6, 6, 6, 6, 6, 6}}, gameState.getGameBoard().getTable());
        gameState.playUserMove("{row: 1, col:4}", "user2");
        gameState.playUserMove("{row: 0, col:4}", "user1");
        assertEquals(new int[][]{{6, 6, 6, 6, 6, 6}, {6, 6, 6, 6, 6, 6}}, gameState.getGameBoard().getTable());
        assertEquals(0, gameState.getGameBoard().getUserOneScore());
        assertEquals(0, gameState.getGameBoard().getUserTwoScore());
        assertEquals(Player.PLAYER_ONE, gameState.getNextUserToPlay());
        assertFalse(gameState.isGameFinished());
    }

    @Test
    public void testPlayerLandsOwnBigPit() {
        gameState.playUserMove("{row: 1, col:0}", "user1");
        gameState.playUserMove("{row: 1, col:1}", "user1");
        assertEquals(new int[][]{{6, 6, 6, 6, 7, 7}, {0, 0, 8, 8, 8, 8}}, gameState.getGameBoard().getTable());
        assertEquals(2, gameState.getGameBoard().getUserOneScore());
        assertEquals(0, gameState.getGameBoard().getUserTwoScore());
        assertEquals(Player.PLAYER_TWO, gameState.getNextUserToPlay());
        assertFalse(gameState.isGameFinished());
    }

    @Test
    public void playerOneCapturesStonesFromPlayerTwosLittlePit() {
        gameState.playUserMove("{row: 1, col:0}", "user1");
        gameState.playUserMove("{row: 1, col:5}", "user1");
        gameState.playUserMove("{row: 0, col:5}", "user2");
        gameState.playUserMove("{row: 1, col:1}", "user1");
        gameState.playUserMove("{row: 0, col:5}", "user2");
        gameState.playUserMove("{row: 1, col:0}", "user1");
        assertEquals(new int[][]{{8, 0, 8, 8, 10, 0}, {0, 0, 8, 8, 8, 1}}, gameState.getGameBoard().getTable());
        assertEquals(12, gameState.getGameBoard().getUserOneScore());
        assertEquals(1, gameState.getGameBoard().getUserTwoScore());
        assertEquals(Player.PLAYER_TWO, gameState.getNextUserToPlay());
        assertFalse(gameState.isGameFinished());
    }

    @Test
    public void testWinnerCalculation() {
        assertNull(gameState.calculateTheWinner());
    }
}
