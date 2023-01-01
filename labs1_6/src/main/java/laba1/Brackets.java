package laba1;

import java.util.ArrayList;
import java.util.List;

class Bracket{
    public Character getLeft() {
        return left;
    }

    public Character getRight() {
        return right;
    }

    public void setLeft(Character left) {
        this.left = left;
    }

    public void setRight(Character right) {
        this.right = right;
    }

    private Character left='(';
    private Character right =')';
}

public  class Brackets{

    public List<Bracket> getBracket() {
        return bracket;
    }

    public void setBracket(List<Bracket> bracket) {
        this.bracket = bracket;
    }
    private List<Bracket> bracket = new ArrayList<Bracket>();
}
