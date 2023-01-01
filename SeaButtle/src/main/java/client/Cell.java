package client;

import Enums.Condition;
import javafx.scene.control.Button;

import static Enums.Condition.*;

public class Cell extends Button {
    public void setFieldAndPosition(Field field, Integer stringIndex, Integer columnIndex){
        this.field=field;
        position=new Position(stringIndex, columnIndex);
    }
    void setFell(){
        setColor(Color.FELL_COLOR);
        setCondition(FELL);

    }
    void setDropShipDeck(){
        setColor(Color.DROP_SHIP_COLOR);
        setCondition(DROP_SHIP_DECK);

    }
    public Condition getCondition() {
        return condition;
    }
    public boolean isShip() {
        return condition.equals(SHIP_DECK);
    }
    public boolean isAlreadyShot(){
        return condition.equals(FELL)||condition.equals(DROP_SHIP_DECK);
    }
    public Condition makeShot(){
        if(!isAlreadyShot()){
            if(isShip()){
                condition = DROP_SHIP_DECK;
                this.setColor(Color.DROP_SHIP_COLOR);
                this.field.fieldScene.setMyShipsCount();
                return  DROP_SHIP_DECK;
            }
            condition=FELL;
            this.setColor(Color.FELL_COLOR);
            System.out.println("cell FELL");
            return FELL;
        }
        System.out.println("cell ALREADY_SHOT");
        return ALREADY_SHOT;
    }
    public void setEmpty(){
        condition=EMPTY;
        this.setColor(Color.EMPTY_COLOR);
    }
    public boolean canAddShip(){
        return !(condition.equals(NEAR_SHIP)||condition.equals(SHIP_DECK));
    }
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    public void setShip(){
        this.condition=SHIP_DECK;
        this.setColor(Color.SHIP_COLOR);
    }

    Cell(){
        setEmpty();

    }
    void setColor(String color){
        this.setStyle("-fx-background-color:"+color);
    }

    public Integer getStringIndex() {
        return position.getStringIndex();
    }
    public Integer getColumnIndex() {
        return position.getColumnIndex();
    }
    private Field field=null;
    class Color{
        public static  String DROP_SHIP_COLOR="#FF0000";
        public static String SHIP_COLOR="#000000";
        public static String FELL_COLOR="#00FF00";
        public static String EMPTY_COLOR="#0000FF";
    }

    Position position=null;
    private Condition condition=Condition.EMPTY;

}
