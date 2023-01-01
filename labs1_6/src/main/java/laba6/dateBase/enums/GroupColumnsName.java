package laba6.dateBase.enums;

public enum GroupColumnsName {
    GROUP_ID(0),
    GROUP_NAME(1),
    STUDENT_ID(2);
    private int num = 0;

    public int getNum() {
        return num;
    }

    boolean equals(String string) {
        return this.toString().equals(string);
    }

    public static GroupColumnsName getValue(String name) throws Throwable  {
        switch (name) {
            case "STUDENT_ID":
                return STUDENT_ID;
            case "GROUP_ID":
                return GROUP_ID;
            case "GROUP_NAME":
                return GROUP_NAME;
        }
       throw new IllegalArgumentException("Unknown"+name);
    }

    GroupColumnsName(int num) {
        this.num = num;
    }

}
