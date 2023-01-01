package client;

import Enums.FieldStatus;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Vector;

class Position{

    private Integer stringIndex=-1;

    public Integer getStringIndex() {
        return stringIndex;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void setStringIndex(Integer stringIndex) {
        this.stringIndex = stringIndex;
    }

    private Integer columnIndex=-1;
    static public Integer makePosition(Integer stringIndex, Integer columnIndex){
        return stringIndex*100+columnIndex;
    }
    static public  Integer makeStringIndex(Integer position){
        return position/100;
    }
    static public  Integer makeColumnIndex(Integer position){
        return position%100;
    }

    Position(Integer stringIndex, Integer columnIndex){
        this.stringIndex= stringIndex;
        this.columnIndex= columnIndex;
    }

}

public abstract class Field {
    static int FIELD_SIZE=10;
    static int MAX_COUNT_SHIPS=20;

    public Integer getCountShip() {
        return countShip;
    }

    protected Integer countShip=0;

    public void incrementShip(){
        countShip++;
    }
    protected Scene fieldScene;
    public void setFieldStatus(FieldStatus fieldStatus) {
        this.fieldStatus = fieldStatus;
    }

    protected FieldStatus fieldStatus = FieldStatus.UNBLOCKED;
    protected Vector<Vector<Cell>> cells;
    Field(GridPane gridPane,  Scene fieldScene){
        this.fieldScene=fieldScene;
        cells=new Vector<Vector<Cell>>();
        for(int cellStringIndex=0; cellStringIndex<FIELD_SIZE; cellStringIndex++){
            Vector<Cell> cellString=new Vector<Cell>();
            for(int cellColumnIndex=0; cellColumnIndex<FIELD_SIZE; cellColumnIndex++){
                Cell cell=new Cell();
                cellString.add(cell);
                addCell(cellStringIndex,cellColumnIndex,gridPane, cell);
            }
            cells.add(cellString);
        }
    }
    public void clearField(){
        for(int cellStringIndex=0; cellStringIndex<FIELD_SIZE; cellStringIndex++){
            for(int cellColumnIndex=0; cellColumnIndex<FIELD_SIZE; cellColumnIndex++){
                cells.get(cellStringIndex).get(cellColumnIndex).setEmpty();
            }
        }
        countShip=-1;
    }

    private void addCell(Integer cellStringIndex,Integer cellColumnIndex,GridPane gridPane, Cell cell){
        cell.setFieldAndPosition(this,cellStringIndex,cellColumnIndex);
        gridPane.add(cell,cellStringIndex,cellColumnIndex);
        cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                theButtonWasPressed(cell);
            }
        });
    }

    abstract void theButtonWasPressed(Cell cell);


}
