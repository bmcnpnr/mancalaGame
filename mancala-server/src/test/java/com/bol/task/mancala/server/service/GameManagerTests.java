package com.bol.task.mancala.server.service;

import com.bol.task.mancala.server.model.GameState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameManagerTests {

    private GameManager gameManager;

    @Before
    public void initTest() {
        gameManager = new GameManager();
        ReflectionTestUtils.setField(gameManager, "applicationContext", mock(ApplicationContext.class));

        when(gameManager.getApplicationContext().getBean("gameState")).thenReturn(
                new GameState(), new GameState(), new GameState(), new GameState(), new GameState(), new GameState()
                , new GameState(), new GameState(), new GameState(), new GameState(), new GameState(), new GameState()
                , new GameState(), new GameState(), new GameState(), new GameState(), new GameState(), new GameState());

    }

    @Test
    public void connectTwoUsersToAGame() {
        Stream.of("user1", "user2").forEach(user -> gameManager.connectTheUser(user));

        Assert.assertNotNull(gameManager.getGameStates());

        Assert.assertEquals(2, gameManager.getGameStates().size());

        Set<String> gameIds = new HashSet<>();

        gameManager.getGameStates().forEach((key, value) -> {
            gameIds.add(value.getGameId());
            assertEquals(2, value.getPlayers().size());
            assertEquals("user1", value.getPlayers().get("player1"));
            assertEquals("user2", value.getPlayers().get("player2"));
            assertFalse(value.isGameFinished());
        });

        assertEquals(1, gameIds.size());
    }

    @Test
    public void connectOneUserToAGame() {
        gameManager.connectTheUser("user3");

        Assert.assertNotNull(gameManager.getGameStates());

        Assert.assertEquals(1, gameManager.getGameStates().size());

        gameManager.getGameStates().forEach((key, value) -> {
            assertEquals(1, value.getPlayers().size());
            assertEquals("user3", value.getPlayers().get("player1"));
            assertNotSame("user3", value.getPlayers().get("player2"));
            assertFalse(value.isGameFinished());
        });
    }

    @Test
    public void connectFourUsersToTwoGames() {
        Stream.of("user4", "user5", "user6", "user7").forEach(user -> gameManager.connectTheUser(user));

        Assert.assertNotNull(gameManager.getGameStates());

        Assert.assertEquals(4, gameManager.getGameStates().size());

        Set<String> gameIds = new HashSet<>();

        gameManager.getGameStates().forEach((key, value) -> {
            gameIds.add(value.getGameId());
            assertEquals(2, value.getPlayers().size());
            String player1 = value.getPlayers().get("player1");
            String player2 = value.getPlayers().get("player2");
            assertTrue("user4".equals(player1) || "user6".equals(player1));
            assertTrue("user5".equals(player2) || "user7".equals(player2));
            assertFalse(value.isGameFinished());
        });

        assertEquals(2, gameIds.size());
    }

    @Test
    public void getGameStateTestAndCheckIfUsersMatch() {
        Stream.of("user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8").forEach(user -> gameManager.connectTheUser(user));
        GameState user1State = gameManager.getGameState("user1");
        GameState user2State = gameManager.getGameState("user2");
        GameState user3State = gameManager.getGameState("user3");
        GameState user4State = gameManager.getGameState("user4");
        GameState user5State = gameManager.getGameState("user5");
        GameState user6State = gameManager.getGameState("user6");
        GameState user7State = gameManager.getGameState("user7");
        GameState user8State = gameManager.getGameState("user8");

        assertEquals(user1State, user2State);
        assertNotSame(user1State, user3State);
        assertNotSame(user1State, user4State);
        assertNotSame(user1State, user5State);
        assertNotSame(user1State, user6State);
        assertNotSame(user1State, user7State);
        assertNotSame(user1State, user8State);
        assertNotSame(user2State, user3State);
        assertNotSame(user2State, user4State);
        assertNotSame(user2State, user5State);
        assertNotSame(user2State, user6State);
        assertNotSame(user2State, user7State);
        assertNotSame(user2State, user8State);

        assertEquals(user3State, user4State);
        assertNotSame(user3State, user5State);
        assertNotSame(user3State, user6State);
        assertNotSame(user3State, user7State);
        assertNotSame(user3State, user8State);
        assertNotSame(user4State, user5State);
        assertNotSame(user4State, user6State);
        assertNotSame(user4State, user7State);
        assertNotSame(user4State, user8State);

        assertEquals(user5State, user6State);
        assertNotSame(user5State, user7State);
        assertNotSame(user5State, user8State);
        assertNotSame(user6State, user7State);
        assertNotSame(user6State, user8State);
        assertEquals(user7State, user8State);
    }
}
