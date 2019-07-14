import SockJS from "sockjs-client";

let stompClient;
export function getStompClient() {    
    const stompConst = require('@stomp/stompjs');
    let socket = new SockJS("http://localhost:8080/mancala-game-websocket");
    stompClient = stompConst.Stomp.over(socket);
    return stompClient;
}

export function sendMessage(message) {
  stompClient.send('/mancalaGame/userMove', {}, message);
}