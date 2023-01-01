package laba6.dateBase.enums;

public enum SubjectColumnsName{
    SUBJECT_ID(0),
    SUBJECT_NAME(1);
    private int num=0;
    boolean equals(String string){
        return this.toString().equals(string);
    }
    public int getNum() {
        return num;
    }
    public static SubjectColumnsName getValue(String name) throws Throwable{
        switch(name){
            case "SUBJECT_ID":
                return SUBJECT_ID;
            case "SUBJECT_NAME":
                return SUBJECT_NAME;
        }
        throw new IllegalArgumentException("Unknown"+name);
    }
    SubjectColumnsName(int num){
        this.num = num;
    }

}