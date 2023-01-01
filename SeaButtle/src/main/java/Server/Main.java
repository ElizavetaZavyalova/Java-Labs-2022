package Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            ServerLobby server = new ServerLobby();
            server.lobbyStart();
        } catch (IOException e) {
            System.out.println("Server socket error: " + e.getMessage());
        }
    }
}
