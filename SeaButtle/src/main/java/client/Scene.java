package client;


import Enums.Location;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import static Enums.FieldStatus.*;
import static Enums.Location.*;


public abstract class Scene {
    protected OpponentField opponentShipsField;
    protected MyField myShipsField;
    protected Location location=VERTICAL;
    protected  Client client;
    protected Integer deck=4;

    protected Integer deckCount=5-deck;
    protected abstract void setMyShipsCount();
    protected abstract void setCurrentStatus(String statusInf);
    protected abstract void setGameStatus(String statusInf);

    protected abstract void nextShip();

    protected abstract void setGiveUp();

    protected abstract void setOpponentShipsCount();

}