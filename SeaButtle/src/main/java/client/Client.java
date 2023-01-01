package client;

import Enums.Condition;
import javafx.animation.AnimationTimer;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import static Enums.Condition.*;
import static Enums.FieldStatus.*;

public class Client {
    private Integer lastShotPosition=-1;
    private Scanner scanner;
    private ResultFromServer resultFromServer;
    private PrintWriter writer;
    private   Scene fieldScene;
    private boolean RUNNING = false;
    private Socket socket;
    void write(String stringToWrite){
        writer.write(stringToWrite+"\n");
        writer.flush();
        System.out.println("was write:"+stringToWrite);
    }
    void startRunning(){
        RUNNING=true;
        new Thread(new ClientReader(scanner, resultFromServer,RUNNING)).start();
        animationTimer.start();
    }
    void stopRunning(){
        if(RUNNING) {
            write("GIVE_UP");
            try {
                socket.close();
                System.out.println("The server is shut down!");
            } catch (IOException e) {
                System.out.println("Socket close error");
            }
        }
        RUNNING=false;
        animationTimer.stop();
    }

    void parseServerResult(){
        switch (resultFromServer.getAction()) {
            case "START":
                start(resultFromServer.getResult());
                break;
            case "SHOT":
                shot(resultFromServer.getResult());
                break;
            case "SHOT_RESULT":
                resultShot(resultFromServer.getResult());
                break;
            case "END_GAME":
                endGame(resultFromServer.getResult());
                break;
            default:
                System.out.println("NOT CORRECT RESULT");
        }
    }
    void endGame(String endGameInformation) {
        fieldScene.setGameStatus("Конец игры");
        fieldScene.setCurrentStatus(endGameInformation);
        stopRunning();
    }
    void start(String turn) {
        fieldScene.setGameStatus("ИГРА");
        fieldScene.setCurrentStatus(turn);
        if (turn.equalsIgnoreCase("Ваша_очередь")) {
            fieldScene.opponentShipsField.setFieldStatus(UNBLOCKED);
        }
    }
    void shot(String shotNumber) {
        Integer result=Integer.parseInt(shotNumber);
        Condition condition=(fieldScene.myShipsField).makeShot(result);
        write(condition.toString());
        if(!fieldScene.myShipsField.isItGameOver()) {
            ifTrueSetTurnUnblocked(condition.equals(FELL));
        }
    }
    void ifTrueSetTurnUnblocked(boolean isItMyTurn) {
        if(isItMyTurn) {
            fieldScene.setCurrentStatus("Ваша_очередь");
            fieldScene.opponentShipsField.setFieldStatus(UNBLOCKED);
            System.out.println("UNBLOCKED");
        }
        else{
            fieldScene.setCurrentStatus("Не ваша_очередь");
            System.out.println("BLOCKED");
        }
    }
    void resultShot(String shotResult){
        this.fieldScene.opponentShipsField.setShotResult(shotResult,lastShotPosition);
        if(!fieldScene.opponentShipsField.isItWin()) {
            ifTrueSetTurnUnblocked(!shotResult.equals(FELL.toString()));
        }
    }
    AnimationTimer animationTimer;

    public Client(Scene fieldScene){
        this.fieldScene =  fieldScene;
        animationTimer=new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (resultFromServer.isAnimate()) {
                    resultFromServer.setAnimate(false);
                    parseServerResult();

                }
            }
        };
    }

    private void connectionServer() throws IOException {
        socket=new Socket();
        stopRunning();
        socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 1019), 2000);
        writer=new PrintWriter(socket.getOutputStream());
        scanner=new Scanner(socket.getInputStream());
        resultFromServer=new ResultFromServer();
        startRunning();
        fieldScene.setGameStatus("Ожидание противника");
    }
    public void tryConnectionServer(){

        try {
            connectionServer();
            fieldScene.setGiveUp();
            fieldScene.deck=-1;
        } catch (IOException  e) {
            fieldScene.setGameStatus("Не выходит соеденить попробуйте еще раз");
            stopRunning();
            System.out.println("Connection problems");
        }
    }

    public void makeShot(Integer shotPosition){
          write(shotPosition.toString());
          lastShotPosition=shotPosition;
          System.out.println("Writer index: " + shotPosition);
    }
    public boolean isGame(){
        return RUNNING;
    }
    public void giveUp(){
        if(RUNNING) {
            stopRunning();
        }
    }
}
