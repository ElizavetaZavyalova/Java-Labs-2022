package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class ServerGame implements Runnable {
    @Override//DROP_SHIP FALL DROP_SHIP_DECK
    public void run() {
        StartGame();
        makeShots();
    }

    void StartGame() {
        write(0, "START", "Ваша_очередь");
        write(1, "START", "Очередь_противника");
    }

    void write(int index, String status, String message) {
        playerSending.get(index).write(status + "\n");
        System.out.println("write " + index + " " + status);
        playerSending.get(index).flush();
        playerSending.get(index).write(message + "\n");
        System.out.println("write " + index + " " + message);
        playerSending.get(index).flush();
    }

    void makeShots() {
        while (GAME) {
            String shot = read(playerScanner.get(currentPlayer));
            System.out.println(currentPlayer + " " + shot);
            sendIfPlayerGiveUp(getNextPlayer(), shot);
            write(getNextPlayer(), "SHOT", shot);

            String shotResult = read(playerScanner.get(getNextPlayer()));
            System.out.println(((currentPlayer + 1) % 2) + " " + shotResult);
            sendIfPlayerGiveUp(currentPlayer, shotResult);
            write(currentPlayer, "SHOT_RESULT", shotResult);

            processingShotResult(shotResult);
        }
    }

    String read(Scanner scanner) {
        while (GAME) {
            if (scanner.hasNext()) {
                return scanner.nextLine();
            }
        }
        return "";
    }

    void sendIfPlayerGiveUp(Integer playerNumber, String playerInformation) {
        if (playerInformation.equals("GIVE_UP")) {
            write(getNextPlayer(), "END_GAME", "Противник сдался вы победили");
            stopServerGame();
        }
    }

    int getNextPlayer() {
        return (currentPlayer + 1) % 2;
    }

    void processingShotResult(String shotResult) {
        if (shotResult.equalsIgnoreCase("FELL")) {
            currentPlayer = getNextPlayer();
            System.out.println("FELL shotResult");
            return;
        }
        if (shotResult.equalsIgnoreCase("DROP_SHIP_DECK")) {
            System.out.println("DROP_SHIP_DECK shotResult");
            if (isItWin()) {
                stopServerGame();
            }
            return;
        }
        System.out.println("NOT CORRECT shotResult");

    }

    boolean isItWin() {
        shipCount[currentPlayer]++;
        System.out.println("countShip+" + currentPlayer + " " + shipCount[currentPlayer]);
        return shipCount[currentPlayer].equals(MAX_COUNT_SHIPS);
    }

    void stopServerGame() {
        GAME = false;
    }

    private Integer MAX_COUNT_SHIPS = 20;
    private boolean GAME = true;
    private Integer currentPlayer = 0;
    private Vector<Scanner> playerScanner = new Vector<>();
    private Integer[] shipCount = {0, 0};
    private Vector<PrintWriter> playerSending = new Vector<>();

    ServerGame(Socket socket1, Socket socket2) throws IOException {
        playerScanner.add(new Scanner(socket1.getInputStream()));
        playerScanner.add(new Scanner(socket2.getInputStream()));
        playerSending.add(new PrintWriter(socket1.getOutputStream()));
        playerSending.add(new PrintWriter(socket2.getOutputStream()));

    }
}
