import SockJS from "sockjs-client";

let stompClient;
export default function connect() {    
    const stompConst = require('@stomp/stompjs');
    let socket = new SockJS("http://localhost:8080/mancala-game-websocket");
    stompClient = stompConst.Stomp.over(socket);
    stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/mancala-notifications', function (message) {
        console.log(JSON.parse(message.body));
        });
      stompClient.send("/mancalaGame/connectToGame");
  });    
}