package com.bol.task.mancala.server.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameManagerTests {

    private GameManager gameManager;
    @Before
    public void initTest() {
        gameManager = new GameManager();
    }
}
