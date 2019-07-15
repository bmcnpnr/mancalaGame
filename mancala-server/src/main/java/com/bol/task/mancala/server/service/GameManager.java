package com.bol.task.mancala.server.service;

import com.bol.task.mancala.server.model.GameState;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.*;

@Service
public class GameManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * Holds <User Session, Game State> pairs
     */
    private Map<String, GameState> gameStates = new LinkedHashMap<>();

    public GameManager() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public GameState getGameState(String userSessionId) {
        return this.gameStates.get(userSessionId);
    }

    /**
     * Assigns the users to game sessions.
     *
     * @param userSessionId
     */
    public void connectTheUser(String userSessionId) {
        if (this.gameStates.get(userSessionId) == null) {
            if (gameStates.size() % 2 == 0) {
                GameState gameState = (GameState) this.applicationContext.getBean("gameState");
                gameState.getPlayers().put("player1", userSessionId);
                gameStates.put(userSessionId, gameState);
            } else {
                GameState gameState = (GameState) gameStates.entrySet().toArray(new Map.Entry[gameStates.size()])[gameStates.size() - 1].getValue();
                gameState.getPlayers().put("player2", userSessionId);
                gameStates.put(userSessionId, gameState);
            }
        } else if (this.getGameState(userSessionId).getPlayers().isEmpty()) {
            this.getGameState(userSessionId).getPlayers().put("player1", userSessionId);
        }
    }

    /**
     * Removes the game if a client disconnects from the server.
     *
     * @param disconnectEvent
     * @throws IOException
     */
    //todo implement the disconnection of the other client connected to the game instance
    @EventListener
    private void onDisconnectEvent(SessionDisconnectEvent disconnectEvent) throws IOException {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        String userSessionId = (String) Objects.requireNonNull(sha.getSessionAttributes()).get("sessionId");
        if (this.gameStates.get(userSessionId) != null)
            removeGameFromServer(this.gameStates.get(userSessionId).getGameId());
    }

    private void removeGameFromServer(String gameId) throws IOException {
        List<String> userSessions = new ArrayList<>();
        for (Map.Entry<String, GameState> entry : this.gameStates.entrySet()) {
            if (entry.getValue().getGameId().equals(gameId)) {
                userSessions.add(entry.getKey());
            }
        }
        userSessions.forEach(userSession -> gameStates.remove(userSession));
    }


}
