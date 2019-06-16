package com.bol.task.mancala.server.service;

import com.bol.task.mancala.server.model.GameState;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * Holds <User Session, Game State> pairs
     */
    private Map<String, GameState> gameStates = new HashMap<>();

    public GameManager() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public GameState getGameState(String userSessionId) {
        return this.gameStates.get(userSessionId);
    }

    public void connectTheUser(String userSessionId) {
        if (this.gameStates.get(userSessionId) == null) {
            GameState gameState = (GameState) this.applicationContext.getBean("gameState");
            gameStates.put(userSessionId, gameState);
        } else if (this.getGameState(userSessionId).getPlayers().isEmpty()) {
            this.getGameState(userSessionId).getPlayers().put("player1", userSessionId);
        }
    }

    @EventListener
    private void onDisconnectEvent(SessionDisconnectEvent disconnectEvent) throws IOException {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        Object userSessionId = sha.getSessionAttributes().get("sessionId");
        System.out.println(userSessionId);
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
