package laba6.dateBase.enums;

public enum TablesName{
    MARK(0),
    STUDENT(1),
    SUBJECT(2),
    GROUP(3);
    private int num=0;

    public int getNum() {
        return num;
    }
    boolean equals(String string){
        return this.toString().equals(string);
    }
    public static TablesName getValue(String name) throws Throwable {
        switch(name){
            case  "STUDENT":
                return STUDENT;
            case "GROUP":
                return  GROUP;
            case  "MARK":
                return MARK;
            case "SUBJECT":
                return  SUBJECT;
        }
        throw new IllegalArgumentException("Unknown"+name);
    }
    TablesName(int num){
        this.num = num;
    }

}
