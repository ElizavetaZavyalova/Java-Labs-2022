package laba6.dateBase.enums;

public enum StudentColumnsName {
    STUDENT_ID(0),
    FIO(1);
    private int num = 0;

    public int getNum() {
        return num;
    }

    public static StudentColumnsName getValue(String name) throws Throwable{
        switch (name) {
            case "STUDENT_ID":
                return STUDENT_ID;
            case "FIO":
                return FIO;
        }
        throw new IllegalArgumentException("Unknown"+name);
    }

    public boolean equals(String string) {
        return this.toString().equals(string);
    }

    StudentColumnsName(int num) {
        this.num = num;
    }

}
