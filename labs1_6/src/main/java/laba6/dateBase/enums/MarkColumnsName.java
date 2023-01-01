package laba6.dateBase.enums;

public enum MarkColumnsName {
    SUBJECT_ID(0),
    STUDENT_ID(1),
    MARK(2),
    DATE(3);
    private int num = 0;

    public int getNum() {
        return num;
    }

    boolean equals(String string) {
        return this.toString().equals(string);
    }

    public static MarkColumnsName getValue(String name) throws Throwable{
        switch (name) {
            case "STUDENT_ID":
                return STUDENT_ID;
            case "MARK":
                return MARK;
            case "SUBJECT_ID":
                return SUBJECT_ID;
            case "DATE":
                return DATE;
        }
        throw new IllegalArgumentException("Unknown"+name);
    }

    MarkColumnsName(int num) {
        this.num = num;
    }

}
