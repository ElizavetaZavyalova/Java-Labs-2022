package client1;


import client.Client;
import client.MyField;
import client.OpponentField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import static Enums.FieldStatus.*;
import static Enums.Location.*;


public class FieldScene extends client.Scene {
    @FXML
    private volatile Button  newGame;
    @FXML
    private volatile Button directionGiveUPConnection;
    @FXML
    private volatile GridPane myField;
    @FXML
    private volatile Label currentStatus;

    @FXML
    private volatile Label gameStatus;
    @FXML
    private volatile Label myShipLabel;
    @FXML
    private volatile Label opponentShipLabel;

    @FXML
    private volatile GridPane opponentField;

    @Override
    public void nextShip(){
        deckCount--;
        if(deckCount==0){
            deck--;
            if(deck!=0){
                setCurrentStatus("Поставьте "+deck.toString()+ "Палубник");
                deckCount=5-deck;
            }
            else {
                myShipsField.setFieldStatus(BLOCKED);
                setCurrentStatus("Корабли расставлены");
                setGameStatus("Нажмите на соединение");
                directionGiveUPConnection.setText("Соеденение");
            }
        }
    }

    @FXML
    void initialize() {
        assertStatus();
        myShipsField = new MyField(myField, this);
        opponentShipsField = new OpponentField(opponentField, this);
        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deck=4;
                deckCount=5-deck;
                opponentShipsField.clearField();
                myShipsField.clearField();
                directionGiveUPConnection.setText(location.toString());
                setGameStatus("Расставьте корабли");
                setMyShipsCount();
                setOpponentShipsCount();
                if(client.isGame()) {
                    stopConnection();
                }
                myShipsField.setFieldStatus(UNBLOCKED);
                opponentShipsField.setFieldStatus(BLOCKED);
            }
        });
        directionGiveUPConnection.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isShipState()) {
                    setGameStatus("Соеденение");
                    client.tryConnectionServer();
                    return;
                }
                else if(isDirection()) {
                    changeDirection();
                }
                else if(isGiveUp()){
                    client.giveUp();
                    directionGiveUPConnection.setText(location.toString());
                    setGameStatus("Конец игры");
                    setCurrentStatus("Вы проиграли");
                }
            }
        });

        client=new Client(this);

    }
    boolean isShipState(){
        return deck.equals(0);
    }
    boolean isDirection(){
        return !client.isGame();
    }
    boolean isGiveUp(){
        return  client.isGame();

    }
    void changeDirection(){
        if (location == VERTICAL) {
            location = HORIZONTAL;
            directionGiveUPConnection.setText("Горизонтально");
        } else if (location == HORIZONTAL) {
            location = VERTICAL;
            directionGiveUPConnection.setText("Вертикально");
        }

    }


    @Override
    public void setGameStatus(String statusInf){
        gameStatus.setText(statusInf);
    }

    @Override
    public void setOpponentShipsCount(){
        opponentShipsField.incrementShip();
        opponentShipLabel.setText(opponentShipsField.getCountShip().toString());
    }
    @Override
    public void setMyShipsCount(){
        myShipsField.incrementShip();
        myShipLabel.setText(myShipsField.getCountShip().toString());
    }
    @Override
    public void setCurrentStatus(String statusInf){
        currentStatus.setText(statusInf);
    }
    @Override
    protected void setGiveUp(){
        directionGiveUPConnection.setText("Сдаться");
    }
    void stopConnection(){
        client.giveUp();
        directionGiveUPConnection.setText(location.toString());
    }
    private void assertStatus(){

    }
}