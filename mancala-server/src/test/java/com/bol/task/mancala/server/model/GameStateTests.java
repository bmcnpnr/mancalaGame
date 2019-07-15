package com.bol.task.mancala.server.model;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameStateTests {

    private GameState gameState;

    @Before
    public void initTest() {
        gameState = new GameState();

        gameState.getPlayers().put("player1","user1");
        gameState.getPlayers().put("player2","user2");
        ReflectionTestUtils.setField(gameState, "gameBoard", Mockito.mock(GameBoard.class));
    }
}
