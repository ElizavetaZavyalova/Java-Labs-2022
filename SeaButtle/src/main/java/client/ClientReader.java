package client;

import java.util.Scanner;

import static java.lang.Thread.sleep;

class ResultFromServer{

    public boolean isAnimate() {
        return animate;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    boolean animate=false;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String action ="";

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private String result ="";
}


public class ClientReader implements  Runnable {
    boolean RUNNING=true;
    @Override
    public void run() {
            while (RUNNING) {
                    resultFromServer.setAction(read());
                    resultFromServer.setResult(read());
                    resultFromServer.setAnimate(true);
            }
    }

    public ClientReader(Scanner scanner, ResultFromServer resultFromServer, boolean RUNNING) {
        this.scanner = scanner;
        this.resultFromServer = resultFromServer;
        this.RUNNING= RUNNING;
    }
    Scanner scanner;
    ResultFromServer resultFromServer;
    private String read(){
        while(RUNNING){
            if(scanner.hasNext()){
                return  scanner.nextLine();
            }
        }
        return "";
    }
}
