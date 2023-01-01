package client;

import Enums.Condition;
import Enums.Location;
import javafx.scene.layout.GridPane;

import static Enums.FieldStatus.*;
import static Enums.Location.*;


public class MyField extends Field {


    public MyField(GridPane gridPane, Scene fieldScene) {
        super(gridPane,fieldScene);
        this.fieldStatus = UNBLOCKED;
    }
    boolean isItGameOver(){
        if(countShip.equals(MAX_COUNT_SHIPS)){
            fieldScene.client.stopRunning();
            this.fieldScene.setCurrentStatus("Вы проиграли");
            this.fieldScene.setGameStatus("Конец игры");
            return true;
        }
        return false;
    }


    @Override
    void theButtonWasPressed(Cell cell) {
        if(fieldStatus.equals(BLOCKED)){
            return;
        }
        setShip(cell.getStringIndex(),cell.getColumnIndex(),fieldScene.deck,fieldScene.location);

    }
    private void setNearShipToCell(int cellStringIndex, int cellColumnIndex){
        if(cellStringIndex<0||cellStringIndex>=FIELD_SIZE||cellColumnIndex<0||cellColumnIndex>=FIELD_SIZE){
            return;
        }
        if(!cells.get(cellStringIndex).get(cellColumnIndex).isShip()){
            cells.get(cellStringIndex).get(cellColumnIndex).setCondition(Condition.NEAR_SHIP);
        }
    }
    private void setNearShip(int cellStringIndex, int cellColumnIndex){
        for(int StringIndex=cellStringIndex-1; StringIndex<=cellStringIndex+1; StringIndex++){
            for(int ColumnIndex=cellColumnIndex-1; ColumnIndex<=cellColumnIndex+1; ColumnIndex++){
                setNearShipToCell(StringIndex,ColumnIndex);
            }
        }
    }
    private boolean isCorrectIndexForDeckCount(int Index, int deckCount){
        return !(FIELD_SIZE-Index<deckCount);
    }
    private boolean canSetVerticalShip(int stringIndex, int columnIndex, int deckCount){
        for (int cellColumnIndex = columnIndex; cellColumnIndex < columnIndex + deckCount; cellColumnIndex++) {
            if (!cells.get(stringIndex).get(cellColumnIndex).canAddShip()) {
                return false;
            }
        }
        return true;

    }
    private void setHorizontalShip(int stringIndex, int columnIndex, int deckCount){
        for(int cellStringIndex = stringIndex; cellStringIndex < stringIndex+deckCount; cellStringIndex++){
            cells.get(cellStringIndex).get(columnIndex).setShip();
        }
        for(int cellStringIndex = stringIndex; cellStringIndex < stringIndex+deckCount; cellStringIndex++){
            setNearShip(cellStringIndex,columnIndex);
        }
    }
    private void setVerticalShip(int stringIndex, int columnIndex, int deckCount ){
        for(int cellColumnIndex = columnIndex; cellColumnIndex < columnIndex+deckCount; cellColumnIndex++){
            cells.get(stringIndex).get(cellColumnIndex).setShip();
        }
        for(int cellColumnIndex = columnIndex; cellColumnIndex < columnIndex+deckCount; cellColumnIndex++){
            setNearShip(stringIndex,cellColumnIndex);
        }
    }

    private boolean canSetHorizontalShip(int stringIndex, int columnIndex, int deckCount ){
        for (int cellStringIndex = stringIndex; cellStringIndex < stringIndex + deckCount; cellStringIndex++) {
            if (!cells.get(cellStringIndex).get(columnIndex).canAddShip()) {
                return false;
            }
        }
        return true;

    }
    public boolean canSetShip(int stringIndex, int columnIndex, int deckCount,Location shipLocation){
        if(shipLocation == VERTICAL&&isCorrectIndexForDeckCount(columnIndex,deckCount)){
            return canSetVerticalShip(stringIndex, columnIndex, deckCount);
        }
        if(shipLocation == HORIZONTAL&&isCorrectIndexForDeckCount(stringIndex,deckCount)){
            return canSetHorizontalShip(stringIndex, columnIndex, deckCount);
        }
        return false;
    }

    public void setShip(int stringIndex, int columnIndex, int deckCount, Location shipLocation){
        if(canSetShip(stringIndex,columnIndex,deckCount,shipLocation)){
            if(shipLocation == VERTICAL){
                setVerticalShip(stringIndex, columnIndex, deckCount);
            }
            if(shipLocation == HORIZONTAL){
                setHorizontalShip(stringIndex, columnIndex, deckCount);
            }
            fieldScene.nextShip();
        }
    }
   public Condition makeShot(Integer position){
       return this.cells.get(Position.makeStringIndex(position)).get(Position.makeColumnIndex(position)).makeShot();

    }
}
