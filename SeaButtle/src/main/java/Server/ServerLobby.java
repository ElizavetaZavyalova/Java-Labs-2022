package Server;
//1234
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class StopServer implements Runnable{
    StopServer(boolean findingConnection){
        this.findingConnection = findingConnection;
    }
    boolean findingConnection;

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        String  ection = in.nextLine();
        while(!(ection).equalsIgnoreCase("STOP")) {
            ection = in.nextLine();
        }
        findingConnection=!findingConnection;
        }


    }
public class ServerLobby {
    ServerSocket mainServer;
    boolean findingConnection = true;
    ServerLobby() throws IOException{
            mainServer = new ServerSocket( 1019);
            System.out.println("Server started"+1019);

    }
    public void lobbyStart() throws IOException {
       new Thread(new StopServer(findingConnection)).start();
        while(findingConnection){
            Socket player1= mainServer.accept();
            System.out.println("player1"+" accept");
            Socket player2= mainServer.accept();
            System.out.println("player2"+" accept");
            new Thread(new ServerGame(player1,player2)).start();
            Thread.yield();

        }
        mainServer.close();
    }
}
