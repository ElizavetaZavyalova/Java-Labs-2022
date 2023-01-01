package client;

import javafx.scene.layout.GridPane;

import static Enums.FieldStatus.*;


public class OpponentField extends Field {
    public OpponentField(GridPane gridPane, Scene fieldScene) {
        super(gridPane,fieldScene);
        this.fieldStatus = BLOCKED;
    }
    @Override
    void theButtonWasPressed(Cell cell) {
             if(fieldStatus.equals(BLOCKED)||cell.isAlreadyShot()){
                 return;
             }
             fieldScene.setCurrentStatus("Вы сделали выстрел");
             this.setFieldStatus(BLOCKED);
             makeShot(Position.makePosition(cell.getStringIndex(),cell.getColumnIndex()));
    }
    public boolean isItWin(){
        if(countShip.equals(MAX_COUNT_SHIPS)){
            fieldScene.client.stopRunning();
            this.setFieldStatus(BLOCKED);
            fieldScene.setCurrentStatus("Вы победили");
            fieldScene.setGameStatus("Конец игры");
            return true;
        }
        return false;
    }

    void makeShot(Integer position){
        fieldScene.client.makeShot(position);
    }
    public void setShotResult(String result, Integer position){
        Cell cell=this.cells.get(Position.makeStringIndex(position)).get(Position.makeColumnIndex(position));
        switch (result) {
            case "FELL":
                cell.setFell();
                break;
            case "DROP_SHIP_DECK":
                cell.setDropShipDeck();
                fieldScene.setOpponentShipsCount();
            default:
                System.out.println("NOT CORRECT actionResult");
        }
    }

}
