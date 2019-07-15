# mancalaGame
Please open the client in this way: A normal tab from "Chrome"/"Firefox"/".." for the first player, and  "a tab from a different browser" / "an incognito tab" for the second client.

I have planned to support multiple game sessions(multiple player pairs), but right now please test it with only two players connected to the game server.
Please don't refresh the pages: it immediately disconnects the user session from the game server because the connection is lost.
How to play:
1. go to "mancala-webapp"
2. run "npm install"
3. run "npm run build"
4. run "npm start" or "serve -s build". (Please open the browser after the game server is running)
5. Run the game server inside the "mancala-server" directory. main method is inside "MancalaServerApplication.java"
